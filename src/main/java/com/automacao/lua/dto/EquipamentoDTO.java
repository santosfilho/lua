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
public class EquipamentoDTO {

    @JsonProperty("id_equipamento")
    private Long idEquipamento;

    private String nome;

    private String descricao;

    private Integer status;

    private String marca;

    @JsonProperty("data_cadastro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataCadastro;

    private Long tombamento;

    @JsonProperty("id_local")
    private Long idLocal;

    @JsonProperty("potencia")
    private Long potencia;

    @ApiModelProperty(name = "id_equipamento", value = "Identificador do equipamento", dataType = "java.lang.Long", required = true)
    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    @ApiModelProperty(name = "nome", value = "Nome do equipamento", dataType = "java.lang.String", required = true)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @ApiModelProperty(name = "descricao", value = "Descrição das caracteristicas do equipamento", dataType = "java.lang.String")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @ApiModelProperty(name = "marca", value = "marca do equipamento", dataType = "java.lang.String", required = true)
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @ApiModelProperty(name = "status", value = "-1: Defeituoso, 0: Desligado, 1: Ligado", dataType = "java.lang.Integer", required = true)
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

    public Long getTombamento() {
        return tombamento;
    }

    public void setTombamento(Long tombamento) {
        this.tombamento = tombamento;
    }

    public Long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Long idLocal) {
        this.idLocal = idLocal;
    }

    @ApiModelProperty(name = "potencia", value = "Potência do equipamento em Watts", dataType = "java.lang.Long")
    public Long getPotencia() {
        return potencia;
    }

    public void setPotencia(Long potencia) {
        this.potencia = potencia;
    }
}
