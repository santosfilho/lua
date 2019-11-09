package com.automacao.lua.service;

import com.automacao.lua.dto.CadastroEquipamentoDTO;
import com.automacao.lua.dto.EquipamentoDTO;
import com.automacao.lua.repository.EquipamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EquipamentoServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipamentoServices.class);

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    CategoriaServices categoriaServices;

    public int mudarStatus(Long idEquipamento, int novoStatus){
        if (novoStatus >= -1 && novoStatus <= 1)
            return equipamentoRepository.mudarStatus(idEquipamento, novoStatus);
        return 0;
    }

    public EquipamentoDTO getEquipamento(Long idEquipamento) {
        List<EquipamentoDTO> equipamento = equipamentoRepository.getEquipamentos(idEquipamento, null, null, null, null, null);
        if (equipamento.size() > 0)
            return equipamento.get(0);
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
                LOGGER.error(e.toString());
                LOGGER.error(e.getLocalizedMessage());
                return null;
            }
        }

        return null;
    }

    public int removerEquipamento(Long idEquipamento){
        return equipamentoRepository.removerEquipamento(idEquipamento);
    }

    public EquipamentoDTO atualizarEquipamento(CadastroEquipamentoDTO equipamento){
        return equipamentoRepository.atualizarEquipamento(equipamento);
    }
}
