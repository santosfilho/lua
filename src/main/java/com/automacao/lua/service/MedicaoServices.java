package com.automacao.lua.service;

import com.automacao.lua.dto.MedicaoDTO;
import com.automacao.lua.dto.MedicaoDetalhadaDTO;
import com.automacao.lua.repository.MedicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class MedicaoServices {
    @Autowired
    private MedicaoRepository medicaoRepository;

    public List<MedicaoDTO> buscarMedicoes(Long idSensor, Timestamp inicio, Timestamp fim) {
        return medicaoRepository.buscarMedicoes(idSensor, inicio, fim);
    }

    public int addMedicaoSensor(MedicaoDetalhadaDTO medicao) {
        if (medicao != null && medicao.getIdSensor() != null && medicao.getMedicao() != null) {
            return medicaoRepository.addMedicaoSensor(medicao.getIdSensor(), medicao.getMedicao());
        }
        return 0;
    }

    public Double ultimaMedicaoSensor(Long idSensor){
        return medicaoRepository.ultimaMedicaoSensor(idSensor);
    }
}
