package com.automacao.lua.repository;

import com.automacao.lua.dto.LocalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocalRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<LocalDTO> getLocais(Long idLocal, Integer capacidade, String localizacao, String descricao, String setor) {
        String sql = "SELECT * FROM public.local WHERE 1=1 ";
        List<Object> params = new ArrayList<>();

        if (idLocal != null) {
            sql += " AND id_local = ? ";
            params.add(idLocal);
        }

        if (capacidade != null) {
            sql += " AND capacidade = ? ";
            params.add(capacidade);
        }

        if (localizacao != null && !localizacao.isEmpty()) {
            sql += " AND localizacao LIKE ('%' || ? || '%')";
            params.add(localizacao);
        }

        if (descricao != null && !descricao.isEmpty()) {
            sql += " AND descricao LIKE ('%' || ? || '%')";
            params.add(descricao);
        }

        if (setor != null && !setor.isEmpty()) {
            sql += " AND setor LIKE ('%' || ? || '%')";
            params.add(setor);
        }

        return jdbcTemplate.query(sql, params.toArray(), new BeanPropertyRowMapper(LocalDTO.class));
    }

}
