package com.automacao.lua.service;

import com.automacao.lua.dto.SensorDTO;
import com.automacao.lua.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SensorServices {

    @Autowired
    private SensorRepository sensorRepository;

    public SensorDTO getSensor(Long idSensor) {
        List<SensorDTO> sensor = sensorRepository.buscarSensores(idSensor, null, null, null, null);
        if (sensor != null && sensor.size() > 0) {
            return sensor.get(0);
        }
        return null;
    }

    public List<SensorDTO> getSensores(Long idEquipamento, Long idTipoSensor, Long idLocal, String siglaUnidadeMedição) {
        return sensorRepository.buscarSensores(null, idTipoSensor, idEquipamento, idLocal, siglaUnidadeMedição);
    }

    public SensorDTO addSensor(SensorDTO sensor) {
        return sensorRepository.addSensor(sensor);
    }

    public int removerSensor(Long idSensor) {
        return sensorRepository.removerSensor(idSensor);
    }

    public SensorDTO atualizarSensor(SensorDTO sensor) {
        return sensorRepository.atualizarSensor(sensor);
    }
}
