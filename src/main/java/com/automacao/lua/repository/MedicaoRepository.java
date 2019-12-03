package com.automacao.lua.repository;

import com.automacao.lua.dto.MedicaoDTO;
import com.automacao.lua.dto.MedicaoDetalhadaDTO;
import com.automacao.lua.service.AlarmeServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * @author José dos Santos Filho (josecnrn@gmail.com)
 * @since 30/10/2019
 */
@Repository
public class MedicaoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AlarmeServices alarmeServices;

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicaoRepository.class);

    /**
     * Quando for adicionada uma medição, ele verá a anterior e a hora que foi adicionada e será salvo a média entre elas.
     * Isso deve ser feito de maneira a persistir apenas persistir no BD apenas de 15 em 15 min.
     * <p>
     * Tensão (ID 1) e corrente(ID 2) será armazenado a cada 5 minutos;
     * Termometro (ID 3) e Proximidade (ID 4) a cada 15 min;
     * Sensor de gás inflamável (ID 5) será armazenado a cada 10 min.
     * <p>
     * Caso haja uma variação maior que X % o valor deve ser salvo.
     * Tensão (ID 1) e Termometro (ID 3) 10%
     * Corrente (ID 2) 200%
     * Gás inflamável (ID 5) 300%
     *
     * @param idSensor identificador do sensor, obrigatório.
     * @param medicao  valor da medição à ser adicionado, obriatório.
     * @return
     */
    public int addMedicaoSensor(Long idSensor, Double medicao, Boolean interno) {
        StringBuilder sql = new StringBuilder(" UPDATE sensor SET medicao = ? WHERE id_sensor = ? RETURNING id_tipo_sensor;");
        Integer idTipoSensor = null;

        double porcentagemDisturbio = 1;
        int step = 1;

        try {
            idTipoSensor = jdbcTemplate.queryForObject(sql.toString(), new Object[]{medicao, idSensor}, Integer.class);
            alarmeServices.verificarAlarmes(idSensor);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.error(ex.toString());
            LOGGER.error(ex.getLocalizedMessage());
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            LOGGER.error(e.getLocalizedMessage());
            return 0;
        }

        int success = (idTipoSensor > 0) ? 1 : 0;

        //Salvar no historico
        if (success == 1) {
            MedicaoDetalhadaDTO ultimaMedicao = ultimaMedicaoHistorico(idSensor);

            switch (idTipoSensor) {
                case 1:
                    step = 300000; //5 min
                    porcentagemDisturbio = 0.1; // 10%
                    break;
                case 2:
                    step = 300000; //5 min
                    porcentagemDisturbio = 2; // 200%
                    break;
                case 3:
                    step = 900000; //15 min
                    porcentagemDisturbio = 0.1; // 10%
                    break;
                case 4:
                    step = 900000; //15 min
                    porcentagemDisturbio = 5; // 500%
                    break;
                case 5:
                    step = 900000; //15 min
                    porcentagemDisturbio = 3; // 300%
                    break;
                default:
                    break;
            }

            //Verifica se a ultima medição já tem mais que o tempo, se sim, cria uma nova, se não atualizao
            if (ultimaMedicao == null || ((ultimaMedicao.getHoraMedicao().getTime() + step) < System.currentTimeMillis())) {
                Calendar momentoMedicao = Calendar.getInstance();

                //Calculo do proximo tempo multiplo do step
                int proxStepMinuto = (momentoMedicao.get(Calendar.MINUTE) / (step / 60000) + 1) * (step / 60000);

                momentoMedicao.set(Calendar.MINUTE, proxStepMinuto);
                momentoMedicao.set(Calendar.SECOND, 0);
                momentoMedicao.set(Calendar.MILLISECOND, 0);

                success = jdbcTemplate.update("INSERT INTO historico_medicoes (id_sensor, hora_medicao, medicao) VALUES (?, ?, ?);",
                        idSensor, momentoMedicao.getTime(), medicao);

            } else {
                success = jdbcTemplate.update(" UPDATE historico_medicoes SET medicao = ? WHERE id_medicao = ? ", medicao, ultimaMedicao.getIdMedicao());

                if (medicao < ultimaMedicao.getMedicao() * (1 - porcentagemDisturbio) || medicao > ultimaMedicao.getMedicao() * (1 + porcentagemDisturbio)) {
                    success = jdbcTemplate.update("INSERT INTO historico_medicoes (id_sensor, hora_medicao, medicao) VALUES (?, DEFAULT, ?);", idSensor, medicao);
                }
            }
        }

        return success;
    }

    public List<MedicaoDTO> buscarMedicoes(Long idSensor, Timestamp inicio, Timestamp fim) {
        return jdbcTemplate.query(" SELECT hm.id_sensor, hm.hora_medicao, hm.medicao FROM historico_medicoes hm WHERE hm.id_sensor = ? AND hm.hora_medicao >= ? AND hm.hora_medicao <= ? ORDER BY hm.hora_medicao DESC",
                new Object[]{idSensor, inicio, fim}, new BeanPropertyRowMapper<>(MedicaoDTO.class));
    }

    private MedicaoDetalhadaDTO ultimaMedicaoHistorico(Long idSensor) {
        List<MedicaoDetalhadaDTO> medicao = jdbcTemplate.query(" SELECT * FROM historico_medicoes WHERE id_sensor = ? ORDER BY hora_medicao DESC LIMIT 1; ",
                new Object[]{idSensor}, new BeanPropertyRowMapper<>(MedicaoDetalhadaDTO.class));

        if (!medicao.isEmpty()) {
            return medicao.get(0);
        }

        return null;
    }

    public Double ultimaMedicaoSensor(Long idSensor){
        return jdbcTemplate.queryForObject(" SELECT medicao FROM sensor WHERE id_sensor = ?; ",
                new Object[]{idSensor}, Double.class);
    }

}
