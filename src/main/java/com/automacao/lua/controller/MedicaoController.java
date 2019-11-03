package com.automacao.lua.controller;


import com.automacao.lua.dto.MedicaoDTO;
import com.automacao.lua.dto.MedicaoDetalhadaDTO;
import com.automacao.lua.service.MedicaoServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/medicoes")
@Api(value = "Medições", description = "Serviço referente as medições do sensor", tags = "Medições")
public class MedicaoController {

    @Autowired
    private MedicaoServices medicaoServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna as mediçoes de acordo com os parametros passador", response = MedicaoDTO.class, httpMethod = "GET", responseContainer = "List")
    public ResponseEntity getAllLocais(
            @ApiParam(name = "idSensor", value = "Identificador do sensor") @RequestParam(value = "idSensor") Long idSensor,
            @ApiParam(name = "inicio", value = "Inicio das medições") @RequestParam(value = "inicio") Timestamp inicio,
            @ApiParam(name = "fim", value = "Fim das medições") @RequestParam(value = "fim") Timestamp fim
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(medicaoServices.buscarMedicoes(idSensor, inicio, fim));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Adiciona uma medicao para o sensor.", response = MedicaoDetalhadaDTO.class, httpMethod = "POST")
    public ResponseEntity addMedicao(
            @ApiParam(name = "Medicao", value = "Medicao") @RequestBody MedicaoDetalhadaDTO medicao
    ) {

        if (medicaoServices.addMedicaoSensor(medicao) > 0)
            return ResponseEntity.status(HttpStatus.CREATED).body("");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar medicao, os campos obrigatorios são idSensor e medicao.");
    }
}
