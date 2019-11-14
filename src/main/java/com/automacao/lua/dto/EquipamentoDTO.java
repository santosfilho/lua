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
@ApiModel(value = "EquipamentoDTO", description = "Representa o equipamento elétrico alvo do controle/medição")
public class EquipamentoDTO extends CadastroEquipamentoDTO{

    @JsonProperty("data_cadastro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataCadastro;

    private String localizacao;

    @JsonProperty("categoria")
    private String categoria;

    @ApiModelProperty(name = "data_cadastro", value = "Data de cadastro do equipamento", dataType = "java.lang.Date")
    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @ApiModelProperty(name = "localizacao", value = "Descrição da localização", dataType = "java.lang.String", required = true)
    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @ApiModelProperty(name = "categoria", value = "Nome da Categoria", dataType = "java.lang.String", required = true)
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
