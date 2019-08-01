package com.automacao.lua.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@ApiModel(value = "EventoDTO", description = "Representa um evento")
public class EventoDTO {
    private Long idEvento;
    private Long idEquipamento;
    private Integer status;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT-3")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT-3")
    private Timestamp hora;

    private String cron;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT-3")
    private Timestamp fimCron;

    @ApiModelProperty(name = "idEvento", value = "Identificador do evento", dataType = "java.lang.Long")
    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    @ApiModelProperty(name = "idEquipamento", value = "Identificador do equipamento", dataType = "java.lang.Long")
    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    @ApiModelProperty(name = "status", value = "Status do equipamento (-1: Defeituoso, 0: Desligado, 1: Ligado) ", dataType = "java.lang.Integer")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ApiModelProperty(name = "hora", value = "Momento em que o evento deve ser executado", dataType = "java.lang.Timestamp")
    public Timestamp getHora() {
        return hora;
    }

    public void setHora(Timestamp hora) {
        this.hora = hora;
    }

    @ApiModelProperty(name = "cron", value = "Rotina do evento de acordo com o padrão da Screduled", dataType = "java.lang.String")
    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @ApiModelProperty(name = "fimCron", value = "Fim do cron", dataType = "java.lang.Timestamp")
    public Timestamp getFimCron() {
        return fimCron;
    }

    public void setFimCron(Timestamp fimCron) {
        this.fimCron = fimCron;
    }
}
