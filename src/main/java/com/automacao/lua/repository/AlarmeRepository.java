package com.automacao.lua.repository;

import com.automacao.lua.dto.AlarmeDTO;
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

    public List<AlarmeDTO> burcarAlarme(Long idAlarme) {
        StringBuilder sql = new StringBuilder("SELECT * FROM alarme_sensor WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (idAlarme != null) {
            sql.append(" AND id_alarme = ? ");
            params.add(idAlarme);
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
            return burcarAlarme(idAlarme).get(0);
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
            return burcarAlarme(alarme.getIdAlarme()).get(0);
        }

        return null;
    }

    public int removerAlarme(Long idAlarme){
        StringBuilder sql = new StringBuilder("DELETE FROM alarme_sensor WHERE id_alarme = ? ;");

        return jdbcTemplate.update(sql.toString(), new Object[]{idAlarme});
    }
}
