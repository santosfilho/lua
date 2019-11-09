package com.automacao.lua.model;

/**
 * Classe que modela as mensagens MQTT com os dados dos sensores
 */
public class SensorDataModel {
    private Long idSensor;
    private Double medicao;

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public Double getMedicao() {
        return medicao;
    }

    public void setMedicao(Double medicao) {
        this.medicao = medicao;
    }
}
