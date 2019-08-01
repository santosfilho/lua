package com.automacao.lua.controller;

import com.automacao.lua.dto.EventoDTO;
import com.automacao.lua.service.EventoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/eventos")
@Api(value = "Eventos", description = "Serviço referente as demandas de eventos", tags = "Eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Cadastra um equipamento.", response = EventoDTO.class, httpMethod = "GET")
    public List<EventoDTO> buscarEventos(
            @ApiParam(name = "idEquipamento", value = "Identificador do equipamento") @RequestParam(value = "idEquipamento", required = false) Long idEquipamento,
            @ApiParam(name = "status", value = "Status do equipamento (-1: Defeituoso, 0: Desligado, 1: Ligado) ") @RequestParam(value = "status", required = false) Integer status,
            @ApiParam(name = "hora", value = "Momento em que o evento deve ser executado") @RequestParam(value = "hora", required = false) Timestamp hora,
            @ApiParam(name = "cron", value = "Rotina do evento de acordo com o padrão da Screduled") @RequestParam(value = "periodo", required = false) String cron,
            @ApiParam(name = "fimCron", value = "Fim do cron") @RequestParam(value = "hora", required = false) Timestamp fimCron
    ) {
        return eventoService.getEventos(idEquipamento, status, hora, cron, fimCron);
    }

    @RequestMapping(value = "/{idEvento}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Retorna um evento de acordo com o id informado.", response = EventoDTO.class, httpMethod = "GET")
    public EventoDTO buscarEvento(
            @ApiParam(name = "idEvento", value = "Identificador do evento") @PathVariable(value = "idEvento") Long idEvento
    ) {
        return eventoService.getEvento(idEvento);
    }

    @RequestMapping(method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Cadastra um evento.", response = EventoDTO.class, httpMethod = "POST")
    public EventoDTO addEvento(
            @ApiParam(name = "evento", value = "Informações do evento") @RequestBody EventoDTO evento
    ) throws Exception {
        return eventoService.addEvento(evento);
    }

    @RequestMapping(value = "/{idEvento}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Deleta um Evento.", response = EventoDTO.class, httpMethod = "DELETE")
    public ResponseEntity deleteEvento(
            @ApiParam(name = "idEvento", value = "Identificador do evento") @PathVariable(value = "idEvento") Long idEvento
    ){
        if (eventoService.removerEvento(idEvento) == 1)
            return new ResponseEntity("Evento removido com sucesso. ", HttpStatus.OK);

        return new ResponseEntity("Evento não encontrado. ", HttpStatus.BAD_REQUEST);
    }
}

