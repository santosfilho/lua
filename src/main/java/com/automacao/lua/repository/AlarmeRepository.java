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

    public List<AlarmeDTO> burcarAlarme(Long idAlarme, Boolean ativo){
        StringBuilder sql = new StringBuilder("SELECT * FROM alarme_sensor WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (idAlarme != null){
            sql.append(" AND id_alarme = ? ");
            params.add(idAlarme);
        }

        if (ativo != null){
            sql.append(" AND ativo = ? ");
            params.add(ativo);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(AlarmeDTO.class));
    }
}
