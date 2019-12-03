package com.automacao.lua.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "LocalDTO", description = "Entidade de representa uma sala, auditorio ou locais de forma geral")
public class LocalDTO {

    private Long idLocal;

    private String localizacao;

    private String setor;

    private Integer capacidade;

    private String descricao;

    @ApiModelProperty(name = "id_local", value = "Identificador do local", dataType = "java.lang.Long", required = true)
    public Long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Long idLocal) {
        this.idLocal = idLocal;
    }

    @ApiModelProperty(name = "localizacao", value = "Descrição da localização", dataType = "java.lang.String", required = true)
    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @ApiModelProperty(name = "setor", value = "Setor da localização", dataType = "java.lang.String", required = true)
    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    @ApiModelProperty(name = "capacidade", value = "Capacidade em número de pessoas do local", dataType = "java.lang.Integer", required = true)
    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    @ApiModelProperty(name = "descricao", value = "scrição/Observações a respeito do local", dataType = "java.lang.String", required = true)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return Array de objetos contendo as caracteristicas do local
     */
    @JsonIgnore
    public List<Object> getLocal(){
        List<Object> local = new ArrayList<>();
        local.add(getLocalizacao());
        local.add(getSetor());
        local.add(getCapacidade());
        local.add(getDescricao());
        return local;
    }
}
