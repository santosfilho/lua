package com.automacao.lua.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author José dos Santos (josecnrn@gmail.com)
 * @since 22/07/19.
 */
@ApiModel(value = "EquipamentoDTO", description = "Representa um equipamento elétrico")
public class EquipamentoDTO {

    @JsonProperty("id_equipamento")
    private Long idEquipamento;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("localizacao")
    private String localizacao;

    @JsonProperty("status")
    private Integer status;

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

    @ApiModelProperty(name = "localizacao", value = "Localização do equipamento", dataType = "java.lang.String", required = true)
    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @ApiModelProperty(name = "status", value = "-1: Defeituoso, 0: Desligado, 1: Ligado", dataType = "java.lang.Integer", required = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
