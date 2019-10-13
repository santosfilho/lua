package com.automacao.lua.controller;

import com.automacao.lua.dto.CategoriaDTO;
import com.automacao.lua.service.CategoriaServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/equipamentos/categorias")
@Api(value = "Equipamentos", description = "Serviço referente os equipamentos", tags = "Categoria")
public class CategoriaController {

    @Autowired
    CategoriaServices categoriaServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna a lista de categorias.", response = CategoriaDTO.class, httpMethod = "GET", responseContainer = "List")
    public List<CategoriaDTO> getAllCategorias(
    ) {
        return categoriaServices.getCategorias();
    }

    @GetMapping(value = "/{idCategoria}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o equipamento do identificador passado.", response = CategoriaDTO.class, httpMethod = "GET")
    public CategoriaDTO getCategoria(
            @ApiParam(name = "idCategoria", value = "Identificador da Categoria") @PathVariable(value = "idCategoria") Long idCategoria
    ) {
        return categoriaServices.getCategoria(idCategoria);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o equipamento do identificador passado.", response = CategoriaDTO.class, httpMethod = "POST")
    public ResponseEntity addCategoria(
            @ApiParam(name = "Categoria", value = "Categoria") @RequestBody CategoriaDTO categoria
    ) {
        CategoriaDTO categ = categoriaServices.addCategoria(categoria);
        if (categ != null)
            return  new ResponseEntity(categ, HttpStatus.OK);

        return new ResponseEntity("Verifique o nome informado", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{idCategoria}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Deleta uma categoria", response = String.class, httpMethod = "DELETE")
    public ResponseEntity removerCategoria(
            @ApiParam(name = "idCategoria", value = "Identificador do equipamento") @PathVariable(value = "idCategoria") Long idCategoria
    ) {
        if (categoriaServices.removerCategoria(idCategoria) == 1)
            return new ResponseEntity("Categoria removido com sucesso.", HttpStatus.OK);

        return new ResponseEntity("Categoria não encontrado.", HttpStatus.BAD_REQUEST);
    }
}
