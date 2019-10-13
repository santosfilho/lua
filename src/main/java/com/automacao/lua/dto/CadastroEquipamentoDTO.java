package com.automacao.lua.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author José dos Santos (josecnrn@gmail.com)
 * @since 22/07/19.
 */
@ApiModel(value = "CadastroEquipamentoDTO", description = "Representa um equipamento elétrico")
public class CadastroEquipamentoDTO {
    private String nome;

    private String descricao;

    private String marca;

    private Long tombamento;

    private Long idLocal;

    @JsonProperty("potencia")
    private Long potencia;

    private Long idCategoria;

    private String modelo;

    private Integer status;

    private Long idEquipamento;

    @ApiModelProperty(name = "idEquipamento", value = "Identificador do equipamento", dataType = "java.lang.Long")
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

    @ApiModelProperty(name = "marca", value = "marca do equipamento", dataType = "java.lang.String")
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @ApiModelProperty(name = "status", value = "-1: Defeituoso, 0: Desligado, 1: Ligado", dataType = "java.lang.Integer")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
