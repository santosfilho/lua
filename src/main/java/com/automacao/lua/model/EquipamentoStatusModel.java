package com.automacao.lua.model;

/**
 * Classe que modela as mensagens MQTT com os dados dos sensores
 */
public class EquipamentoStatusModel {
    private Long idEquipamento;
    private Integer status;

    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
