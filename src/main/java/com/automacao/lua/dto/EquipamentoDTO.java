package com.automacao.lua.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author José dos Santos (josecnrn@gmail.com)
 * @since 22/07/19.
 */
@ApiModel(value = "EquipamentoDTO", description = "Representa um equipamento elétrico")
public class EquipamentoDTO extends CadastroEquipamentoDTO{

    private Long idEquipamento;

    private Integer status;

    @JsonProperty("data_cadastro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataCadastro;

    @ApiModelProperty(name = "idEquipamento", value = "Identificador do equipamento", dataType = "java.lang.Long")
    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    @ApiModelProperty(name = "status", value = "-1: Defeituoso, 0: Desligado, 1: Ligado", dataType = "java.lang.Integer")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ApiModelProperty(name = "data_cadastro", value = "Data de cadastro do equipamento", dataType = "java.lang.Date")
    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}
