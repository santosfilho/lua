package com.automacao.lua.service;

import com.automacao.lua.dto.AlarmeDTO;
import com.automacao.lua.repository.AlarmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlarmeServices {

    @Autowired
    private AlarmeRepository alarmeRepository;

    public List<AlarmeDTO> getAlarmes(){
        return alarmeRepository.burcarAlarme(null);
    }

    public AlarmeDTO getAlarme(Long idAlarme){
        List<AlarmeDTO> alarme = alarmeRepository.burcarAlarme(idAlarme);
        if (alarme != null && alarme.size() > 0){
            return alarme.get(0);
        }
        return null;
    }

    public AlarmeDTO atualizarAlarme(AlarmeDTO alarme){
        return alarmeRepository.atualizarAlarme(alarme);
    }

    public AlarmeDTO addAlarme(AlarmeDTO alarme){
        return alarmeRepository.addAlarme(alarme);
    }

    public int removerAlarme(Long idAlarme){
        return alarmeRepository.removerAlarme(idAlarme);
    }
}
