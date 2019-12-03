package com.automacao.lua.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SensorDTO", description = "Entidade de representa um sensor de um equipamento ou local.")
public class SensorDTO {
    private Long idSensor;
    private Long idEquipamento;
    private Long idLocal;
    private Long idTipoSensor;
    private Double medicao;
    private String siglaUnidade;

    @ApiModelProperty(name = "idSensor", value = "Identificador do sensor. ", dataType = "java.lang.Long", required = true)
    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    @ApiModelProperty(name = "idSensor", value = "Identificador do sensor. ", dataType = "java.lang.Long", required = true)
    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    @ApiModelProperty(name = "idSensor", value = "Identificador do sensor. ", dataType = "java.lang.Long")
    public Long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Long idLocal) {
        this.idLocal = idLocal;
    }

    @ApiModelProperty(name = "medicao", value = "Valor real medido (ultima medição) pelo sensor na unidade padrão do mesmo.", dataType = "java.lang.Double")
    public Double getMedicao() {
        return medicao;
    }

    public void setMedicao(Double medicao) {
        this.medicao = medicao;
    }

    @ApiModelProperty(name = "idTipoSensor", value = "Identificador do tipo do tipo do sensor. ", dataType = "java.lang.Long", required = true)
    public Long getIdTipoSensor() {
        return idTipoSensor;
    }

    public void setIdTipoSensor(Long idTipoSensor) {
        this.idTipoSensor = idTipoSensor;
    }

    @ApiModelProperty(name = "siglaUnidade", value = "Sigla da unidade de medida.", dataType = "java.lang.String")
    public String getSiglaUnidade() {
        return siglaUnidade;
    }

    public void setSiglaUnidade(String siglaUnidade) {
        this.siglaUnidade = siglaUnidade;
    }
}
