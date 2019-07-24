package com.automacao.lua.controller;

import com.automacao.lua.dto.EquipamentoDTO;
import com.automacao.lua.dto.LocalDTO;
import com.automacao.lua.service.LocalServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locais")
public class LocalController {

    @Autowired
    LocalServices localServices;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna a lista de locais que correspondam aos parâmetros informados.", response = EquipamentoDTO.class, httpMethod = "GET", responseContainer = "List")
    public List<LocalDTO> getAllEquipamentos(
            @ApiParam(name = "localizacao", value = "Número de tombamento do equipamento") @RequestParam(value = "localizacao", required = false) String localizacao,
            @ApiParam(name = "capacidade", value = "Nome do equipamento") @RequestParam(value = "capacidade", required = false) Integer capacidade,
            @ApiParam(name = "descricao", value = "Descricao do local") @RequestParam(value = "descricao", required = false) String descricao,
            @ApiParam(name = "setor", value = "Número de tombamento do equipamento") @RequestParam(value = "setor", required = false) String setor
    ) {
        return localServices.getLocais(capacidade, localizacao, descricao, setor);
    }

    @RequestMapping(value = "/{id_local}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o local do identificador passado.", response = LocalDTO.class, httpMethod = "GET")
    public LocalDTO getEquipamento(
            @ApiParam(name = "id_local", value = "Identificador do local") @PathVariable(value = "id_local") Long idLocal
    ) {
        return localServices.getLocal(idLocal);
    }
}
