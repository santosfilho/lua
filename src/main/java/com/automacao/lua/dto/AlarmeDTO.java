package com.automacao.lua.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "AlarmeDTO", description = "Entidade responsável por armazenar os alarmes e as ações condicionadas aos valores dos sensores.")
public class AlarmeDTO {

    private Long idAlarme;
    private Long idEquipamento;
    private Integer status;
    private Long idSensor;
    private Boolean ativo;
    private Integer condicao;

    public Long getIdAlarme() {
        return idAlarme;
    }

    public void setIdAlarme(Long idAlarme) {
        this.idAlarme = idAlarme;
    }

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

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getCondicao() {
        return condicao;
    }

    public void setCondicao(Integer condicao) {
        this.condicao = condicao;
    }
}
