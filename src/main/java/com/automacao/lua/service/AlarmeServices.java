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

    public List<AlarmeDTO> getAlarmes(Long idSensor){
        return alarmeRepository.burcarAlarme(null, idSensor);
    }

    public AlarmeDTO getAlarme(Long idAlarme){
        List<AlarmeDTO> alarme = alarmeRepository.burcarAlarme(idAlarme, null);
        if (alarme != null && !alarme.isEmpty()){
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

    /**
     * Verifica se os alarmes deve ser executado ou não para determinado idSensor.
     *
     * @param idSensor deverá ser usado para filtra os alarmes vinculados ao sensor.
     */
    public void verificarAlarmes(Long idSensor) {
        List<AlarmeDTO> alarmes = getAlarmes(idSensor);
        verificarAlarmes(alarmes);
    }

    public void verificarAlarmes(List<AlarmeDTO> alarmes){
        alarmeRepository.verificarAlarmes(alarmes);
    }

}
