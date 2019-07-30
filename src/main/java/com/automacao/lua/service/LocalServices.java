package com.automacao.lua.service;

import com.automacao.lua.dto.LocalDTO;
import com.automacao.lua.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocalServices {
    @Autowired
    LocalRepository localRepository;

    public LocalDTO getLocal(Long idLocal){
        return localRepository.getLocais(idLocal, null, null, null, null).get(0);
    }

    public List<LocalDTO> getLocais(Integer capacidade, String localizacao, String descricao, String setor) {
        return localRepository.getLocais(null, capacidade, localizacao, descricao, setor);
    }
}
