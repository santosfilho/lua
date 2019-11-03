package com.automacao.lua.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@ApiModel(value = "MedicaoDTO", description = "Entidade que representa uma mediçao do sensor.")
public class MedicaoDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT-3")
    private Timestamp horaMedicao;

    private Double medicao;

    public MedicaoDTO() {}

    public MedicaoDTO(Timestamp horaMedicao, Double medicao) {
        this.horaMedicao = horaMedicao;
        this.medicao = medicao;
    }

    @ApiModelProperty(name = "horaMedicao", value = "Hora da medicao", dataType = "java.lang.Timestamp")
    public Timestamp getHoraMedicao() {
        return horaMedicao;
    }

    public void setHoraMedicao(Timestamp horaMedicao) {
        this.horaMedicao = horaMedicao;
    }

    @ApiModelProperty(name = "medicao", value = "Valor da medição", dataType = "java.lang.Double", required = true)
    public Double getMedicao() {
        return medicao;
    }

    public void setMedicao(Double medicao) {
        this.medicao = medicao;
    }
}
