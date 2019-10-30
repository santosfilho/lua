package com.automacao.lua.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AlarmeDTO", description = "Entidade responsável por armazenar os alarmes e as ações condicionadas aos valores dos sensores.")
public class AlarmeDTO {

    private Long idAlarme;
    private Long idEquipamento;
    private Integer status;
    private Long idSensor;
    private Integer condicao;
    private Double valorDisparo;

    @ApiModelProperty(name = "idAlarme", value = "Identificador do Alarme", dataType = "java.lang.Long", required = true)
    public Long getIdAlarme() {
        return idAlarme;
    }

    public void setIdAlarme(Long idAlarme) {
        this.idAlarme = idAlarme;
    }

    @ApiModelProperty(name = "idEquipamento", value = "Identificador do Equipamento", dataType = "java.lang.Long", required = true)
    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    @ApiModelProperty(name = "status", value = "Novo Status do Equipamento", dataType = "java.lang.Integer", required = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ApiModelProperty(name = "idSensor", value = "Identificador do Sensor", dataType = "java.lang.Long", required = true)
    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    @ApiModelProperty(name = "condicao", value = "Condição igual a 1-Igual, 2-Menor, 3-Maior, 4-Menor igual e 5-Maior Igual.", dataType = "java.lang.Integer", required = true)
    public Integer getCondicao() {
        return condicao;
    }

    public void setCondicao(Integer condicao) {
        this.condicao = condicao;
    }

    @ApiModelProperty(name = "condicao", value = "Valor de referencia à ser cruzado com o valor atual do equipamento para que o alarme seja executado caso passe na condição. ", dataType = "java.lang.Double", required = true)
    public Double getValorDisparo() {
        return valorDisparo;
    }

    public void setValorDisparo(Double valorDisparo) {
        this.valorDisparo = valorDisparo;
    }

    @JsonIgnore
    public Boolean alarmeValido(){
        return getIdEquipamento() != null && getIdSensor() != null && getCondicao() != null && getStatus() != null && getIdAlarme() != null;
    }
}
