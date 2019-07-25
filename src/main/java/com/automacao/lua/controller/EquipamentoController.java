package com.automacao.lua.controller;

import com.automacao.lua.dto.EquipamentoDTO;
import com.automacao.lua.service.EquipamentoServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    EquipamentoServices equipamentoServices;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna a lista de equipamentos que correspondam aos parâmetros informados.", response = EquipamentoDTO.class, httpMethod = "GET", responseContainer = "List")
    public List<EquipamentoDTO> getAllEquipamentos(
            @ApiParam(name = "tombamento", value = "Número de tombamento do equipamento") @RequestParam(value = "tombamento", required = false) Long tombamento,
            @ApiParam(name = "id_local", value = "Identificador da localização do equipamento") @RequestParam(value = "id_local", required = false) Long idLocal,
            @ApiParam(name = "nome", value = "Nome do equipamento") @RequestParam(value = "nome", required = false) String nome,
            @ApiParam(name = "marca", value = "Marca do equipamento") @RequestParam(value = "marca", required = false) String marca,
            @ApiParam(name = "status", value = "Número de tombamento do equipamento") @RequestParam(value = "tombamento", required = false) Integer status
    ) {
        return equipamentoServices.getEquipamentos(idLocal, tombamento, nome, marca, status);
    }

    @RequestMapping(value = "/{id_equipamento}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o equipamento do identificador passado.", response = EquipamentoDTO.class, httpMethod = "GET")
    public EquipamentoDTO getEquipamento(
            @ApiParam(name = "id_equipamento", value = "Identificador do equipamento") @PathVariable(value = "id_equipamento") Long idEquipamento
    ) {
        return equipamentoServices.getEquipamento(idEquipamento);
    }


}
