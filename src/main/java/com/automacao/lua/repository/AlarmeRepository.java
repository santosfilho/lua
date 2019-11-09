package com.automacao.lua.repository;

import com.automacao.lua.components.JavaMailApp;
import com.automacao.lua.dto.AlarmeDTO;
import com.automacao.lua.service.EquipamentoServices;
import com.automacao.lua.service.MedicaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlarmeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EquipamentoServices equipamentoServices;

    @Autowired
    private MedicaoServices medicaoServices;

    @Autowired
    private JavaMailApp javaMailApp;

    public List<AlarmeDTO> burcarAlarme(Long idAlarme, Long idSensor) {
        StringBuilder sql = new StringBuilder("SELECT * FROM alarme_sensor WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (idAlarme != null) {
            sql.append(" AND id_alarme = ? ");
            params.add(idAlarme);
        }

        if (idSensor != null) {
            sql.append(" AND id_sensor = ? ");
            params.add(idSensor);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(AlarmeDTO.class));
    }

    public AlarmeDTO addAlarme(AlarmeDTO alarme) {
        String sqlColunas = " INSERT INTO alarme_sensor (id_alarme ";
        String sqlValores = " VALUES (DEFAULT ";
        List<Object> params = new ArrayList<>();

        if (alarme.getIdEquipamento() != null) {
            sqlColunas += ", id_equipamento ";
            sqlValores += ", ? ";
            params.add(alarme.getIdEquipamento());
        }

        if (alarme.getStatus() != null) {
            sqlColunas += ", status ";
            sqlValores += ", ? ";
            params.add(alarme.getStatus());
        }

        if (alarme.getIdSensor() != null) {
            sqlColunas += ", id_sensor ";
            sqlValores += ", ? ";
            params.add(alarme.getIdSensor());
        }

        if (alarme.getValorDisparo() != null) {
            sqlColunas += ", valor_disparo ";
            sqlValores += ", ? ";
            params.add(alarme.getValorDisparo());
        }

        if (alarme.getCondicao() != null) {
            sqlColunas += ", condicao ";
            sqlValores += ", ? ";
            params.add(alarme.getCondicao());
        }

        sqlColunas += ") ";
        sqlValores += ") RETURNING id_alarme;";

        Long idAlarme = jdbcTemplate.queryForObject(sqlColunas + sqlValores, params.toArray(), Long.class);

        if (idAlarme != null && idAlarme > 0) {
            return burcarAlarme(idAlarme, null).get(0);
        }

        return null;
    }

    public AlarmeDTO atualizarAlarme(AlarmeDTO alarme){
        //UPDATE "public"."alarme_sensor SET "id_equipamento" = 3, "status" = 0, "valor_disparo" = 11, "condicao" = 3 WHERE "id_alarme" = 3
        List<Object> params = new ArrayList<>();
        String sql = "UPDATE alarme_sensor SET id_alarme = ? ";
        params.add(alarme.getIdAlarme());

        if (alarme.getIdEquipamento() != null) {
            sql += ", id_equipamento = ? ";
            params.add(alarme.getIdEquipamento());
        }

        if (alarme.getStatus() != null) {
            sql += ", status = ? ";
            params.add(alarme.getStatus());
        }

        if (alarme.getValorDisparo() != null) {
            sql += ", valor_disparo = ? ";
            params.add(alarme.getValorDisparo());
        }

        if (alarme.getCondicao() != null) {
            sql += ", condicao = ? ";
            params.add(alarme.getCondicao());
        }

        if (alarme.getIdSensor() != null) {
            sql += ", id_sensor = ? ";
            params.add(alarme.getIdSensor());
        }

        sql += " WHERE id_alarme = ? RETURNING id_alarme ";
        params.add(alarme.getIdAlarme());

        if (jdbcTemplate.queryForObject(sql, params.toArray(), Long.class) != null) {
            return burcarAlarme(alarme.getIdAlarme(), null).get(0);
        }

        return null;
    }

    public int removerAlarme(Long idAlarme){
        StringBuilder sql = new StringBuilder("DELETE FROM alarme_sensor WHERE id_alarme = ? ;");

        return jdbcTemplate.update(sql.toString(), new Object[]{idAlarme});
    }

    /**
     * Verifica se o alarme deve ser executado ou não e o executa, assim como se for o caso,
     * notifica ao usuario.
     *
     * @param alarmes recebe todos os alarmes cadastrados
     */
    public void verificarAlarmes(List<AlarmeDTO> alarmes) {
        for (AlarmeDTO alarme : alarmes) {
            Double medicao = medicaoServices.ultimaMedicaoSensor(alarme.getIdSensor());
            boolean exec = false;

            switch (alarme.getCondicao()) {
                case 1: // IGUAL
                    exec = alarme.getValorDisparo().equals(medicao);
                    break;
                case 2: // MENOR
                    exec = alarme.getValorDisparo() < medicao;
                    break;
                case 3: // MAIOR
                    exec = alarme.getValorDisparo() > medicao;
                    break;
                case 4: // MENOR IGUAL
                    exec = alarme.getValorDisparo() <= medicao;
                    break;
                case 5: // MAIOR IGUAL
                    exec = alarme.getValorDisparo() >= medicao;
                    break;
                default:
                    break;
            }

            if (exec) {
                equipamentoServices.mudarStatus(alarme.getIdEquipamento(), alarme.getStatus());

                if (alarme.getNotificar()) {
                    List<String> emails = new ArrayList<>();
                    emails.add("josefilhocnrn@gmail.com");

                    StringBuilder mensagem = new StringBuilder();
                    mensagem.append("Equipamento: ").append(equipamentoServices.getEquipamento(alarme.getIdEquipamento()).getNome());
                    mensagem.append(".\n").append(alarme.getStatus() == 1 ? "LIGOU" : "DESLIGOU");
                    mensagem.append("\n Caso não queira ser notificado, desmarque a opção de notificar de alarme.");

                    javaMailApp.enviarEmail(emails, "Alarme disparou!! ", mensagem.toString());

                }
            }
        }
    }


}
