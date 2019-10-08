package com.automacao.lua.repository;

import com.automacao.lua.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoriaRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<CategoriaDTO> getCategorias(Long idCategoria) {
        String sql = "SELECT * FROM public.categoria WHERE 1=1 ";
        List<Object> params = new ArrayList<>();

        if (idCategoria != null) {
            sql += " AND id_categoria = ? ";
            params.add(idCategoria);
        }

        return jdbcTemplate.query(sql, params.toArray(), new BeanPropertyRowMapper(CategoriaDTO.class));
    }

    public CategoriaDTO addCategoria(CategoriaDTO categoria) {
        String sql = "INSERT INTO public.categoria (id_categoria, nome) VALUES (DEFAULT, ? ) ";

        int sucess = jdbcTemplate.update(sql, categoria.getNome());

        if (sucess == 1)
            return ((List<CategoriaDTO>) jdbcTemplate.query("SELECT * FROM categoria " +
                    "ORDER BY id_categoria DESC LIMIT 1", new Object[]{}, new BeanPropertyRowMapper(CategoriaDTO.class))).get(0);

        return null;
    }

    public int removerCategoria(Long id_categoria) {
        String sql = "DELETE FROM local WHERE id_categoria = ? ";

        int sucess = jdbcTemplate.update(sql, new Object[]{id_categoria});

        return sucess;
    }
}
