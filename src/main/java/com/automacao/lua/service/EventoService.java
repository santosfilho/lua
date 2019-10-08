package com.automacao.lua.service;

import com.automacao.lua.dto.EventoDTO;
import com.automacao.lua.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public EventoDTO getEvento(Long idEvento) {
        if (eventoRepository.getEventos(idEvento, null, null, null, null,null).size() > 0)
            return eventoRepository.getEventos(idEvento, null, null, null, null,null).get(0);
        return null;
    }

    public List<EventoDTO> getEventos(Long idEquipamento, Integer status, Timestamp hora, String cron, Timestamp fimCron){
        return eventoRepository.getEventos(null, idEquipamento, status, hora, cron, fimCron);
    }

    public EventoDTO addEvento(EventoDTO evento) throws Exception {
        return eventoRepository.addEvento(evento);
    }

    public int updateEvento(EventoDTO evento){
        return eventoRepository.updateEvento(evento);
    }

    public int removerEvento(Long idEvento){
        return eventoRepository.removerEvento(idEvento);
    }
}
