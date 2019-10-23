package com.automacao.lua.repository;

import com.automacao.lua.dto.MedicaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicaoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * A função deve calular e armazenar o valor medio das medições em intervalos de 15 min. 0 15 30 45
     *
     * PARA OS TIPOS 1 E 2, VOLTIMETRO E AMPERIMENTRO
     *
     * @param idSensor
     * @param valoresMedidos
     * @param inicioMedicao
     * @param fimMedicao
     * @return
     */
    public int addMedicaoMediaSensor(Long idSensor, List<Double> valoresMedidos, Timestamp inicioMedicao, Timestamp fimMedicao) {
        /*
         * primeiro eu descubro a hora que deve armazenar
         *
         * depois a calculo a medida media
         *
         */

        Double media = null;
        for (Double valor : valoresMedidos) {
            media += valor;
        }

        media = media / valoresMedidos.size();

        int success = 0;
        if (idSensor != null && media != null) {
            success = jdbcTemplate.queryForObject("UPDATE sensor SET medicao = ? WHERE id_sensor = ? RETURNING id_sensor;",
                    new Object[]{idSensor, media}, Long.class) != null ? 1 : 0;
        }

        if (success == 1){
            jdbcTemplate.update("INSERT INTO historico_medicoes (id_sensor, hora_medicao, medicao) VALUES (?, ?, ?);",
                    new Object[]{idSensor, fimMedicao, media});
        }


//        Integer tempoMedido = Math.toIntExact((fimMedicao.getTime() - inicioMedicao.getTime()) / (1000));
//        int qtdIntervalos = tempoMedido / 900; //Quantidade de blocos de 15 min
//        int qtdValoresPorIntervalo = valoresMedidos.size() / qtdIntervalos;
//        int ultimoIndice = 0;
//        Double totalIntervalo = null;
//        List<MedicaoDTO> medicoes = new ArrayList<>();
//
//        while (inicioMedicao.getTime() < fimMedicao.getTime()) {
//            for (int i = 0; i <= qtdIntervalos; i++) {
//                for (int j = 0; j <= qtdValoresPorIntervalo; j++) {
//                    totalIntervalo += valoresMedidos.get(j + ultimoIndice);
//                }
//            }
//
//            MedicaoDTO med = new MedicaoDTO(inicioMedicao, valoresMedidos.get(0));
//
//        }

        return success;
    }

    /**
     * Quando for adicionada uma medição, ele verá a anterior e a hora que foi adicionada e será salvo a média entre elas.
     * Isso deve ser feito de maneira a persistir apenas persistir no BD apenas de 15 em 15 min.
     *
     * @param idSensor
     * @param medicao
     * @return
     */
    public int addMedicaoSensor(Long idSensor, Double medicao) {
        StringBuilder sql = new StringBuilder(" UPDATE sensor SET medicao = ? WHERE id_sensor = ? RETURNING id_sensor;");

        int success = 0;
        if (idSensor != null && medicao != null) {
            success = jdbcTemplate.queryForObject(sql.toString(), new Object[]{idSensor, medicao}, Long.class) != null ? 1 : 0;
        }

        //Salvar no historico
        if (success == 1) {
            MedicaoDTO ultimaMedicao = ultimaMedicaoHistorico(idSensor);
            //Verifica se a ultima medição já tem mais de 15 min, se sim cria uma nova
            if (ultimaMedicao == null || ((ultimaMedicao.getHoraMedicao().getTime() + 900000) < System.currentTimeMillis())){
                jdbcTemplate.update("INSERT INTO historico_medicoes (id_sensor, hora_medicao, medicao) VALUES (?, DEFAULT, ?);",
                        new Object[]{idSensor, medicao});
            } else {
                medicao = (ultimaMedicao.getMedicao() + medicao) / 2;
                jdbcTemplate.update(" UPDATE historico_medicoes SET medicao = ? WHERE id_medicao = ? ",
                        new Object[]{medicao, ultimaMedicao.getIdMedicao()});
            }
        }

        return success;
    }

    public List<MedicaoDTO> buscarMedicoes(Long idSensor, Timestamp inicio, Timestamp fim) {
        StringBuilder sql = new StringBuilder(" SELECT hm.id_sensor, hm.hora_medicao, hm.medicao FROM historico_medicoes hm " +
                " WHERE hm.id_sensor = ? AND hm.hora_medicao >= ? AND hm.hora_medicao <= ? ");

        List<MedicaoDTO> medicoes = jdbcTemplate.query(sql.toString(), new Object[]{idSensor, inicio, fim}, new BeanPropertyRowMapper<>(MedicaoDTO.class));

        return medicoes;
    }

    private MedicaoDTO ultimaMedicaoHistorico(Long idSensor) {
        StringBuilder sql = new StringBuilder(" SELECT * FROM historico_medicoes WHERE id_sensor = ? ORDER BY hora_medicao DESC LIMIT 1; ");

        return jdbcTemplate.queryForObject(sql.toString(), new Object[]{idSensor}, MedicaoDTO.class);
    }

}
