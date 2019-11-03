package com.automacao.lua.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@ApiModel(value = "MedicaoDetalhadaDTO", description = "Entidade que representa uma mediçao do sensor.")
public class MedicaoDetalhadaDTO extends MedicaoDTO{

    @JsonIgnore
    private Long idMedicao;

    private Long idSensor;

    public MedicaoDetalhadaDTO(Timestamp horaMedicao, Double medicao) {
        super(horaMedicao, medicao);
    }

    public MedicaoDetalhadaDTO() {}

    @ApiModelProperty(name = "idSensor", value = "Identificador do sensor", dataType = "java.lang.Long", required = true)
    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    @JsonIgnore
    public Long getIdMedicao() {
        return idMedicao;
    }

    public void setIdMedicao(Long idMedicao) {
        this.idMedicao = idMedicao;
    }

}
