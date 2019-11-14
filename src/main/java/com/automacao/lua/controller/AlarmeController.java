package com.automacao.lua.controller;

import com.automacao.lua.dto.AlarmeDTO;
import com.automacao.lua.service.AlarmeServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/alarmes")
@Api(value = "Alarmes dos Sensores", description = "Serviço responsavel pelas requições referentes aos alarmes dos sensores e suas respectivas ações. ", tags = "Alarmes")
public class AlarmeController {

    @Autowired
    private AlarmeServices alarmeServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna uma lista com os alarmes existentes. ", response = AlarmeDTO.class, httpMethod = "GET")
    public ResponseEntity buscarAlarmes(
            @ApiParam(name = "idAlarme", value = "Identificador do Alarme") @RequestParam(name = "idSensor", required = false) Long idAlarme
    ){
        return ResponseEntity.status(HttpStatus.OK).body(alarmeServices.getAlarmes(idAlarme));
    }

    @GetMapping(value = "/{idAlarme}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o alarme de acordo com seu identificador.", response = AlarmeDTO.class, httpMethod = "GET")
    public ResponseEntity getAlarme(
            @ApiParam(name = "idAlarme", value = "Identificador do Alarme.") @PathVariable(value = "idAlarme") Long idAlarme
    ){
        return ResponseEntity.status(HttpStatus.OK).body(alarmeServices.getAlarme(idAlarme));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Cadastra um Alarme.", response = AlarmeDTO.class, httpMethod = "POST")
    public ResponseEntity addAlarme(
            @ApiParam(name = "alarme", value = "Informações do Alarme") @RequestBody AlarmeDTO alarme
    ) {
        AlarmeDTO novoAlarme = null;

        if (alarme != null && alarme.alarmeValido()){
            novoAlarme = alarmeServices.addAlarme(alarme);
        }

        if (novoAlarme != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAlarme);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados de cadastro incompletos.");
    }

    @RequestMapping(value = "/{idAlarme}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Deleta um Alarme.", response = AlarmeDTO.class, httpMethod = "DELETE")
    public ResponseEntity deleteAlarme(
            @ApiParam(name = "idAlarme", value = "Identificador do Alarme") @PathVariable(value = "idAlarme") Long idAlarme
    ){
        if (alarmeServices.removerAlarme(idAlarme) == 1)
            return new ResponseEntity("Alarme removido com sucesso. ", HttpStatus.OK);

        return new ResponseEntity("Alarme não encontrado. ", HttpStatus.BAD_REQUEST);
    }
}
