package com.automacao.lua.service;

import com.automacao.lua.dto.EquipamentoDTO;
import com.automacao.lua.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoServices {

    @Autowired
    EquipamentoRepository equipamentoRepository;

    public EquipamentoDTO getEquipamento(Long idEquipamento){
        return equipamentoRepository.getEquipamentos(idEquipamento, null, null, null, null).get(0);
    }

    public List<EquipamentoDTO> getEquipamentos(Long tombamento, String nome, String marca, Integer status) {
        return equipamentoRepository.getEquipamentos(null, tombamento, nome, marca, status);
    }
}
