package com.automacao.lua.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe que modela as mensagens MQTT com os dados dos sensores
 */
@ApiModel(value = "EquipamentoStatusModel", description = "Representa modelo do tópico é destinado a troca de mensagens referente ao status do equipamento")
public class SensorDataModel {
    private Long idSensor;
    private Double medicao;

    @ApiModelProperty(name = "idSensor", value = "Identificador do Sensor", dataType = "java.lang.Long", required = true)
    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    @ApiModelProperty(name = "medicao", value = "Identificador do Equipamento", dataType = "java.lang.Double", required = true)
    public Double getMedicao() {
        return medicao;
    }

    public void setMedicao(Double medicao) {
        this.medicao = medicao;
    }
}
