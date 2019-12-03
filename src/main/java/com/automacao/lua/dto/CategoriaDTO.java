package com.automacao.lua.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author José dos Santos (josecnrn@gmail.com)
 * @since 22/07/19.
 */
@ApiModel(value = "CategoriaDTO", description = "Representa uma categoria de equipamento elétrico")
public class CategoriaDTO {
    private Long idCategoria;

    private String nome;

    @ApiModelProperty(name = "idCategoria", value = "Nome da Categoria", dataType = "java.lang.Long", required = true)
    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    @ApiModelProperty(name = "nome", value = "Nome da Categoria", dataType = "java.lang.String", required = true)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
