package com.automacao.lua.repository;

import com.automacao.lua.components.MqttApp;
import com.automacao.lua.dto.CadastroEquipamentoDTO;
import com.automacao.lua.dto.EquipamentoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EquipamentoRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private MqttApp mqttApp;

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipamentoRepository.class);

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
        StringBuilder sql = new StringBuilder(" SELECT equipamento.*, local.localizacao, categoria.nome as categoria FROM equipamento equipamento ");
        sql.append(" JOIN local local on (local.id_local = equipamento.id_local) ");
        sql.append(" JOIN categoria categoria on (categoria.id_categoria = equipamento.id_categoria) ");
        sql.append(" WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (idEquipamento != null) {
            sql.append(" AND id_equipamento = ? ");
            params.add(idEquipamento);
        }

        if (idLocal != null) {
            sql.append(" AND id_local = ? ");
            params.add(idLocal);
        }

        if (tombamento != null) {
            sql.append(" AND tombamento = ? ");
            params.add(tombamento);
        }

        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND sem_acento(nome) ILIKE sem_acento('%' || ? || '%') ");
            params.add(nome);
        }

        if (marca != null && !marca.isEmpty()) {
            sql.append(" AND sem_acento(marca) ILIKE sem_acento('%' || ? || '%') ");
            params.add(marca);
        }

        if (status != null) {
            sql.append(" AND status = ? ");
            params.add(status);
        }

        sql.append(" ORDER BY id_equipamento ASC ");

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper(EquipamentoDTO.class));
    }


    public EquipamentoDTO cadastrarEquipamento(CadastroEquipamentoDTO equipamento) {
        int sucess = jdbcTemplate.update(" INSERT INTO public.equipamento VALUES (DEFAULT, ?, ?, 0, ?, ?, ?, ?, ?, ?, ?) ", equipamento.getListObjetos().toArray());
        if (sucess == 1)
            return ultimoEquipamento();
        return null;
    }

    /**
     * Metodo responsavel por alterar o status do equipamento atraves do envio da mensagem MQTT,
     * caso sucesso no envio da mensagem o status do equipamento é atualizado no BD.
     *
     * @param idEquipamento Identificador do Equipamento.
     * @param novoStatus Status que o equipamento deve receber.
     * @return 0 caso tenha falhado e 1 caso tenha obtido sucesso.
     */
    public int mudarStatus(Long idEquipamento, int novoStatus, boolean interno) {
        int sucesso = 0;
        if (idEquipamento != null) {
            try {
                if (interno) {
                    sucesso = jdbcTemplate.update("UPDATE equipamento SET status = ? WHERE equipamento.id_equipamento = ? ", novoStatus, idEquipamento);
                } else {
                    if (mqttApp.enviarMensagem(idEquipamento, novoStatus)) {
                        sucesso = jdbcTemplate.update("UPDATE equipamento SET status = ? WHERE equipamento.id_equipamento = ? ", novoStatus, idEquipamento);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.toString());
            }
        }
        return sucesso;
    }

    /**
     * Atualiza o equipamento de acordo com o idEquipamento passado em CadastroEquipamentoDTO
     *
     * @param equipamento CadastroEquipamentoDTO
     * @return EquipamentoDTO
     */
    public EquipamentoDTO atualizarEquipamento(CadastroEquipamentoDTO equipamento) {
        List<Object> params = new ArrayList<>();
        String sql = "UPDATE equipamento SET id_equipamento = ? ";
        params.add(equipamento.getIdEquipamento());

        if (equipamento.getNome() != null && !equipamento.getNome().isEmpty()) {
            sql += ", nome = ? ";
            params.add(equipamento.getNome());
        }

        if (equipamento.getDescricao() != null && !equipamento.getDescricao().isEmpty()) {
            sql += ", descricao = ? ";
            params.add(equipamento.getDescricao());
        }

        if (equipamento.getStatus() != null) {
            sql += ", status    = ? ";
            params.add(equipamento.getStatus());
        }

        if (equipamento.getIdLocal() != null && equipamento.getIdLocal() > 0) {
            sql += ", id_local  = ? ";
            params.add(equipamento.getIdLocal());
        }

        if (equipamento.getIdCategoria() != null && equipamento.getIdCategoria() > 0) {
            sql += ", id_categoria  = ? ";
            params.add(equipamento.getIdCategoria());
        }

        if (equipamento.getTombamento() != null && equipamento.getTombamento() > 0) {
            sql += ", tombamento  = ? ";
            params.add(equipamento.getTombamento());
        }

        if (equipamento.getMarca() != null && !equipamento.getMarca().isEmpty()) {
            sql += ", marca  = ? ";
            params.add(equipamento.getMarca());
        }

        if (equipamento.getModelo() != null && !equipamento.getModelo().isEmpty()) {
            sql += ", modelo  = ? ";
            params.add(equipamento.getModelo());
        }

        if (equipamento.getPotencia() != null && equipamento.getPotencia() > 0) {
            sql += ", potencia  = ? ";
            params.add(equipamento.getPotencia());
        }

        sql += " WHERE equipamento.id_equipamento = ? ";
        params.add(equipamento.getIdEquipamento());

        if (params.size() > 2 && jdbcTemplate.update(sql, params.toArray()) == 1)
            return getEquipamentos(equipamento.getIdEquipamento(), null, null, null, null, null).get(0);

        return null;
    }

    public int removerEquipamento(Long idEquipamento) {
        StringBuilder sql = new StringBuilder(" DELETE FROM equipamento WHERE id_equipamento = ? ");
        return jdbcTemplate.update(sql.toString(), idEquipamento);
    }

    /**
     * @return O ultumo equipamento casdastrado
     */
    private EquipamentoDTO ultimoEquipamento() {
        List<EquipamentoDTO> equipamentos = ((List<EquipamentoDTO>) jdbcTemplate.query("SELECT * FROM public.equipamento ORDER BY id_equipamento DESC LIMIT 1 ", new Object[]{}, new BeanPropertyRowMapper(EquipamentoDTO.class)));
        if (equipamentos.isEmpty()){
            return null;
        } else {
            return equipamentos.get(0);
        }

    }
}
