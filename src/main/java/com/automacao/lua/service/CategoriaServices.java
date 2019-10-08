package com.automacao.lua.service;

import com.automacao.lua.dto.CategoriaDTO;
import com.automacao.lua.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaServices {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaDTO getCategoria(Long idCategoria){
        if (categoriaRepository.getCategorias(idCategoria).size() > 0)
            return categoriaRepository.getCategorias(idCategoria).get(0);
        return null;
    }

    public List<CategoriaDTO> getCategorias(){
        return categoriaRepository.getCategorias(null);
    }

    public CategoriaDTO addCategoria(CategoriaDTO categoria){
        return categoriaRepository.addCategoria(categoria);
    }

    public int removerCategoria(Long idCategoria){
        return categoriaRepository.removerCategoria(idCategoria);
    }
}
