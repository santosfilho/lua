package com.automacao.lua.dto;

import java.sql.Timestamp;

public class MedicaoDTO {
    private Long idMedicao;
    private Long idSensor;
    private Timestamp horaMedicao;
    private Double medicao;

    public MedicaoDTO(Timestamp horaMedicao, Double medicao) {
        this.horaMedicao = horaMedicao;
        this.medicao = medicao;
    }

    public Timestamp getHoraMedicao() {
        return horaMedicao;
    }

    public void setHoraMedicao(Timestamp horaMedicao) {
        this.horaMedicao = horaMedicao;
    }

    public Double getMedicao() {
        return medicao;
    }

    public void setMedicao(Double medicao) {
        this.medicao = medicao;
    }

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public Long getIdMedicao() {
        return idMedicao;
    }

    public void setIdMedicao(Long idMedicao) {
        this.idMedicao = idMedicao;
    }
}
