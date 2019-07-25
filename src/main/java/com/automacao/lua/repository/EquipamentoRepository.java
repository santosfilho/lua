package com.automacao.lua.repository;

import com.automacao.lua.dto.EquipamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EquipamentoRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Função responsavel por retornar uma lista de equipamentos de acodo com os parametros passados
     *
     * @param idEquipamento
     * @param tombamento
     * @param nome
     * @param marca
     * @param status
     * @return
     */
    public List<EquipamentoDTO> getEquipamentos(Long idEquipamento, Long idLocal, Long tombamento, String nome, String marca, Integer status) {
        String sql = "SELECT * FROM public.equipamento WHERE 1=1 ";
        List<Object> params = new ArrayList<>();

        if (idEquipamento != null) {
            sql += " AND id_equipamento = ? ";
            params.add(idEquipamento);
        }

        if (idLocal != null) {
            sql += " AND id_local = ? ";
            params.add(idLocal);
        }

        if (tombamento != null) {
            sql += " AND tombamento = ? ";
            params.add(tombamento);
        }

        if (nome != null && !nome.isEmpty()) {
            sql += " AND sem_acento(nome) ILIKE sem_acento('%' || ? || '%') ";
            params.add(nome);
        }

        if (marca != null && !marca.isEmpty()) {
            sql += " AND sem_acento(marca) ILIKE sem_acento('%' || ? || '%') ";
            params.add(marca);
        }

        if (status != null) {
            sql += " AND status = ? ";
            params.add(status);
        }

        return jdbcTemplate.query(sql, params.toArray(), new BeanPropertyRowMapper(EquipamentoDTO.class));
    }

    public void cadastrarEquipamento(Long idEquipamento, Long idLocal, Long tombamento, String nome, String marca, Integer status){

    }
}
