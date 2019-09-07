package com.automacao.lua.service;

import com.automacao.lua.dto.CadastroEquipamentoDTO;
import com.automacao.lua.dto.EquipamentoDTO;
import com.automacao.lua.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EquipamentoServices {

    @Autowired
    EquipamentoRepository equipamentoRepository;

    public EquipamentoDTO getEquipamento(Long idEquipamento) {
        if (equipamentoRepository.getEquipamentos(idEquipamento, null, null, null, null, null).size() > 0)
            return equipamentoRepository.getEquipamentos(idEquipamento, null, null, null, null, null).get(0);
        return null;
    }

    public List<EquipamentoDTO> getEquipamentos(Long idLocal, Long tombamento, String nome, String marca, Integer status) {
        return equipamentoRepository.getEquipamentos(null, idLocal, tombamento, nome, marca, status);
    }

    public EquipamentoDTO cadastrarEquipamento(CadastroEquipamentoDTO equipamento) {
        if (equipamento.getNome() != null && !equipamento.getNome().isEmpty()) {
            try {
                return equipamentoRepository.cadastrarEquipamento(equipamento);
            } catch (Exception e) {
                System.out.println("Erro ao adicionar equipamento");
                return null;
            }
        }

        return null;
    }

    public int removerEquipamento(Long idEquipamento){
        return equipamentoRepository.removerEquipamento(idEquipamento);
    }
}
