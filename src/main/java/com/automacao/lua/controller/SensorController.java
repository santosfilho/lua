package com.automacao.lua.controller;

import com.automacao.lua.dto.SensorDTO;
import com.automacao.lua.service.SensorServices;
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
@RequestMapping("/sensores")
@Api(value = "Sensores", description = "Serviço referente as demandas dos Sensores", tags = "Sensores")
public class SensorController {

    @Autowired
    private SensorServices sensorServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna uma lista de sensores de acordo com os parametros passados.", response = SensorDTO.class, httpMethod = "GET")
    public ResponseEntity buscarSensores(
        @ApiParam(name = "idEquipamento", value = "Identificador do Equipamento.") @RequestParam(value = "idEquipamento", required = false) Long idEquipamento,
        @ApiParam(name = "idTipoSensor", value = "Identificador do tipo do sensor.") @RequestParam(value = "idTipoSensor", required = false) Long idTipoSensor,
        @ApiParam(name = "idLocal", value = "Identificador do Local.") @RequestParam(value = "idLocal", required = false) Long idLocal,
        @ApiParam(name = "siglaUnidade", value = "Sigla da unidade de Medição.") @RequestParam(value = "siglaUnidade", required = false) String siglaUnidadeMedicao
    ){
        return ResponseEntity.status(HttpStatus.OK).body(sensorServices.getSensores(idEquipamento, idTipoSensor, idLocal, siglaUnidadeMedicao));
    }

    @GetMapping(value = "/{idSensor}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna o sensor de acordo com o identificador.",response = SensorDTO.class, httpMethod = "GET")
    public ResponseEntity getSensor(
        @ApiParam(name = "idSensor", value = "Identificador do Sensor.") @PathVariable(value = "idSensor") Long idSensor
    ){
        return ResponseEntity.status(HttpStatus.OK).body(sensorServices.getSensor(idSensor));
    }

    @RequestMapping(method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Cadastra um sensor.", response = SensorDTO.class, httpMethod = "POST")
    public ResponseEntity addSensor(
            @ApiParam(name = "sensor", value = "Informações do sensor") @RequestBody SensorDTO sensor
    ) throws Exception {
        SensorDTO novoEvento = null;

        if (sensor != null && (sensor.getIdEquipamento() != null || sensor.getIdLocal() != null) && sensor.getIdTipoSensor() != null){
            novoEvento = sensorServices.addSensor(sensor);
        }

        if (novoEvento != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEvento);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados de cadastro invalidos.");
    }

    @RequestMapping(value = "/{idSensor}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Deleta um Sensor.", response = SensorDTO.class, httpMethod = "DELETE")
    public ResponseEntity deleteSensor(
            @ApiParam(name = "idSensor", value = "Identificador do Sensor") @PathVariable(value = "idSensor ") Long idSensor
    ){
        if (sensorServices.removerSensor(idSensor) == 1)
            return new ResponseEntity("Sensor removido com sucesso. ", HttpStatus.OK);

        return new ResponseEntity("Sensor não encontrado. ", HttpStatus.BAD_REQUEST);
    }
}
