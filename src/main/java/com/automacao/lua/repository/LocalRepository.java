package com.automacao.lua.repository;

import com.automacao.lua.dto.LocalDTO;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocalRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * @param idLocal
     * @param capacidade
     * @param localizacao
     * @param descricao
     * @param setor
     * @return
     */
    public List<LocalDTO> getLocais(Long idLocal, Integer capacidade, String localizacao, String descricao, String setor) {
        String sql = "SELECT * FROM public.local WHERE 1=1 ";
        List<Object> params = new ArrayList<>();

        if (idLocal != null) {
            sql += " AND id_local = ? ";
            params.add(idLocal);
        }

        if (capacidade != null) {
            sql += " AND capacidade = ? ";
            params.add(capacidade);
        }

        if (localizacao != null && !localizacao.isEmpty()) {
            sql += " AND localizacao LIKE ('%' || ? || '%')";
            params.add(localizacao);
        }

        if (descricao != null && !descricao.isEmpty()) {
            sql += " AND descricao LIKE ('%' || ? || '%')";
            params.add(descricao);
        }

        if (setor != null && !setor.isEmpty()) {
            sql += " AND setor LIKE ('%' || ? || '%')";
            params.add(setor);
        }

        return jdbcTemplate.query(sql, params.toArray(), new BeanPropertyRowMapper(LocalDTO.class));
    }

    /**
     * @param local
     * @return
     */
    public LocalDTO addLocal(LocalDTO local) {
        String sql = "INSERT INTO public.local (id_local, localizacao, setor, capacidade, descricao) VALUES (DEFAULT, ?, ?, ?, ?) ";

        int sucess = jdbcTemplate.update(sql, local.getLocal().toArray());

        if (sucess == 1)
            return ((List<LocalDTO>) jdbcTemplate.query("SELECT id_local, localizacao, setor, capacidade, descricao FROM local ORDER BY id_local DESC LIMIT 1", new Object[]{}, new BeanPropertyRowMapper(LocalDTO.class))).get(0);

        return null;
    }

    public LocalDTO atualizarLocal(LocalDTO local) {

        List<Object> params = new ArrayList<>();
        String sql = " UPDATE local SET id_local = ? ";
        params.add(local.getIdLocal());

        if (local.getLocalizacao() != null && ! local.getLocalizacao().isEmpty()){
            sql+=", localizacao = ? ";
            params.add(local.getLocalizacao());
        }

        if (local.getSetor() != null && ! local.getSetor().isEmpty()){
            sql+=", setor = ? ";
            params.add(local.getSetor());
        }

        if (local.getCapacidade() != null){
            sql+=", capacidade = ? ";
            params.add(local.getCapacidade());
        }

        if (local.getDescricao() != null && ! local.getDescricao().isEmpty()){
            sql+=", descricao = ? ";
            params.add(local.getDescricao());
        }

        sql += " WHERE id_local = ? ";
        params.add(local.getIdLocal());
        if(jdbcTemplate.update(sql, params.toArray()) == 1){
            return (getLocais(local.getIdLocal(), null, null, null, null)).get(0);
        }

        return null;
    }

    /**
     * @param idLocal
     * @return
     */
    public int removerLocal(Long idLocal) {
        String sql = "DELETE FROM local WHERE id_local = ? ";

        int sucess = 0;
        try{
            sucess = jdbcTemplate.update(sql, new Object[]{idLocal});
        } catch (Exception e){
            e.printStackTrace();
        }

        return sucess;
    }

}
