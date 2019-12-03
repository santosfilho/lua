package com.automacao.lua.dto;

public class TipoSensorDTO {
    private Long idTipoSensor;
    private String descricao;
    private String unidade;
    private String siglaUnidade;

    public Long getIdTipoSensor() {
        return idTipoSensor;
    }

    public void setIdTipoSensor(Long idTipoSensor) {
        this.idTipoSensor = idTipoSensor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getSiglaUnidade() {
        return siglaUnidade;
    }

    public void setSiglaUnidade(String siglaUnidade) {
        this.siglaUnidade = siglaUnidade;
    }
}
