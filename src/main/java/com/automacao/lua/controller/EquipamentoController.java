package com.automacao.lua.controller;

import com.automacao.lua.dto.CadastroEquipamentoDTO;
import com.automacao.lua.dto.CategoriaDTO;
import com.automacao.lua.dto.EquipamentoDTO;
import com.automacao.lua.service.CategoriaServices;
import com.automacao.lua.service.EquipamentoServices;
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
@RequestMapping("/equipamentos")
@Api(value = "Equipamentos", description = "Serviço referente os equipamentos", tags = "Equipamentos")
public class EquipamentoController {

    @Autowired
    EquipamentoServices equipamentoServices;

    @Autowired
    CategoriaServices categoriaServices;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna a lista de equipamentos que correspondam aos parâmetros informados.", response = EquipamentoDTO.class, httpMethod = "GET", responseContainer = "List")
    public List<EquipamentoDTO> getAllEquipamentos(
            @ApiParam(name = "tombamento", value = "Número de tombamento do equipamento") @RequestParam(value = "tombamento", required = false) Long tombamento,
            @ApiParam(name = "idLocal", value = "Identificador da localização do equipamento") @RequestParam(value = "idLocal", required = false) Long idLocal,
            @ApiParam(name = "nome", value = "Nome do equipamento") @RequestParam(value = "nome", required = false) String nome,
            @ApiParam(name = "marca", value = "Marca do equipamento") @RequestParam(value = "marca", required = false) String marca,
            @ApiParam(name = "status", value = "Status do equipamento (-1: Defeituoso, 0: Desligado, 1: Ligado) ") @RequestParam(value = "tombamento", required = false) Integer status
    ) {
        return equipamentoServices.getEquipamentos(idLocal, tombamento, nome, marca, status);
    }

    @RequestMapping(value = "/{idEquipamento}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o equipamento do identificador passado.", response = EquipamentoDTO.class, httpMethod = "GET")
    public EquipamentoDTO getEquipamento(
            @ApiParam(name = "idEquipamento", value = "Identificador do equipamento") @PathVariable(value = "idEquipamento") Long idEquipamento
    ) {
        return equipamentoServices.getEquipamento(idEquipamento);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Cadastra um equipamento.", response = EquipamentoDTO.class, httpMethod = "POST")
    public ResponseEntity cadastrarEquipamento(
            @ApiParam(name = "Equipamento", value = "Equipamento") @RequestBody CadastroEquipamentoDTO equipamento
    ) {
        CadastroEquipamentoDTO novoEquipamento = equipamentoServices.cadastrarEquipamento(equipamento);

        if (novoEquipamento != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEquipamento);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar equipamento, verifique idLocal e idCategoria.");
    }

    @RequestMapping(value = "/{idEquipamento}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Deleta um equipamento", response = String.class, httpMethod = "DELETE")
    public ResponseEntity removerEquipamento(
            @ApiParam(name = "idEquipamento", value = "Identificador do equipamento") @PathVariable(value = "idEquipamento") Long idEquipamento
    ) {
        if (equipamentoServices.removerEquipamento(idEquipamento) == 1)
            return new ResponseEntity("Equipamento removido com sucesso.", HttpStatus.OK);

        return new ResponseEntity("Equipamento não encontrado.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/categorias", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna a lista de categorias.", response = CategoriaDTO.class, httpMethod = "GET", responseContainer = "List")
    public List<CategoriaDTO> getAllCategorias(
    ) {
        return categoriaServices.getCategorias();
    }

    @RequestMapping(value = "/categorias/{idCategoria}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o equipamento do identificador passado.", response = CategoriaDTO.class, httpMethod = "GET")
    public CategoriaDTO getCategoria(
            @ApiParam(name = "idCategoria", value = "Identificador da Categoria") @PathVariable(value = "idCategoria") Long idCategoria
    ) {
        return categoriaServices.getCategoria(idCategoria);
    }

    @RequestMapping(value = "/categorias", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o equipamento do identificador passado.", response = CategoriaDTO.class, httpMethod = "POST")
    public ResponseEntity addCategoria(
            @ApiParam(name = "Categoria", value = "Categoria") @RequestBody CategoriaDTO categoria
    ) {
        CategoriaDTO categ = categoriaServices.addCategoria(categoria);
        if (categ != null)
            return  new ResponseEntity(categ, HttpStatus.OK);

        return new ResponseEntity("Verifique o nome informado", HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/categorias/{idCategoria}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Deleta uma categoria", response = String.class, httpMethod = "DELETE")
    public ResponseEntity removerCategoria(
            @ApiParam(name = "idCategoria", value = "Identificador do equipamento") @PathVariable(value = "idCategoria") Long idCategoria
    ) {
        if (categoriaServices.removerCategoria(idCategoria) == 1)
            return new ResponseEntity("Categoria removido com sucesso.", HttpStatus.OK);

        return new ResponseEntity("Categoria não encontrado.", HttpStatus.BAD_REQUEST);
    }
}
