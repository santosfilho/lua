package com.automacao.lua.controller;

import com.automacao.lua.dto.LocalDTO;
import com.automacao.lua.service.LocalServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locais")
@Api(value = "Locais", description = "Serviço referente aos locais", tags = "Locais")
public class LocalController {

    @Autowired
    LocalServices localServices;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna a lista de locais que correspondam aos parâmetros informados.", response = LocalDTO.class, httpMethod = "GET", responseContainer = "List")
    public List<LocalDTO> getAllLocais(
            @ApiParam(name = "localizacao", value = "Número de tombamento do equipamento") @RequestParam(value = "localizacao", required = false) String localizacao,
            @ApiParam(name = "capacidade", value = "Nome do equipamento") @RequestParam(value = "capacidade", required = false) Integer capacidade,
            @ApiParam(name = "descricao", value = "Descricao do local") @RequestParam(value = "descricao", required = false) String descricao,
            @ApiParam(name = "setor", value = "Número de tombamento do equipamento") @RequestParam(value = "setor", required = false) String setor
    ) {
        return localServices.getLocais(capacidade, localizacao, descricao, setor);
    }

    @RequestMapping(value = "/{id_local}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o local do identificador passado.", response = LocalDTO.class, httpMethod = "GET")
    public LocalDTO getLocal(
            @ApiParam(name = "id_local", value = "Identificador do local") @PathVariable(value = "id_local") Long idLocal
    ) {
        return localServices.getLocal(idLocal);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Cadastra um local e o retorna em caso de sucesso.", response = LocalDTO.class, httpMethod = "POST")
    public ResponseEntity addLocal(
            @ApiParam(name = "local", value = "Local a ser criado") @RequestBody LocalDTO local
    ) {
        LocalDTO novoLocal = localServices.addLocal(local);
        if (novoLocal != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(novoLocal);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados de cadastro invalidos, por favor verifique os campos informados.");
    }

    @RequestMapping(value = "/{id_local}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Remove um local de acordo com o identificador passado.", response = String.class, httpMethod = "DELETE")
    public ResponseEntity removeLocal(
            @ApiParam(name = "id_local", value = "Identificador do local") @PathVariable(value = "id_local") Long idLocal
    ) {
        if (localServices.removerLocal(idLocal) == 1)
            return ResponseEntity.status(HttpStatus.OK).body("Local excluido com sucesso.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Identificador passado não existe.");
    }
}
