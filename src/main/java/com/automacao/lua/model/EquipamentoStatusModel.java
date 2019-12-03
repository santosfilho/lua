package com.automacao.lua.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe que modela as mensagens MQTT com os dados dos sensores
 */
@ApiModel(value = "EquipamentoStatusModel", description = "Representa modelo do tópico é destinado a troca de mensagens referente ao status do equipamento")
public class EquipamentoStatusModel {
    private Long idEquipamento;
    private Integer status;

    @ApiModelProperty(name = "idEquipamento", value = "Identificador do Equipamento", dataType = "java.lang.Long", required = true)
    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    @ApiModelProperty(name = "status", value = "Status que o equipamento possui ou deve possuir", dataType = "java.lang.Integer", required = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
