package com.automacao.lua.repository;

import com.automacao.lua.dto.SensorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SensorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SensorDTO> buscarSensores(Long idSensor, Long idTipoSensor, Long idEquipamento, Long idLocal, String siglaUnidadeMedicao){
        StringBuilder sql = new StringBuilder(" select sensor.id_sensor, sensor.id_tipo_sensor, sensor.id_equipamento, sensor.id_local, sensor.medicao, tipo.sigla_unidade, tipo.descricao " +
                " FROM sensor sensor " +
                " JOIN tipo_sensor tipo on tipo.id_tipo_sensor = sensor.id_tipo_sensor " +
                " WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (idSensor != null){
            sql.append(" AND sensor.id_sensor = ? ");
            params.add(idSensor);
        }

        if (idTipoSensor != null){
            sql.append(" AND sensor.id_tipo_sensor = ? ");
            params.add(idTipoSensor);
        }

        if (idEquipamento != null){
            sql.append(" AND sensor.id_equipamento = ? ");
            params.add(idEquipamento);
        }

        if (idLocal != null){
            sql.append(" AND sensor.id_local = ? ");
            params.add(idLocal);
        }

        if (siglaUnidadeMedicao != null){
            sql.append(" AND tipo.sigla_unidade ILIKE ? ");
            params.add(siglaUnidadeMedicao);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper(SensorDTO.class));
    }

    /**
     *
     * @param idTipoSensor (Obrigatório)
     * @param idEquipamento
     * @param idLocal
     * @return
     */
    public SensorDTO addSensor(Long idTipoSensor, Long idEquipamento, Long idLocal){
        String sqlColunas = " INSERT INTO public.sensor (id_sensor ";
        String sqlValores = " VALUES (DEFAULT ";
        List<Object> params = new ArrayList<>();

        if (idTipoSensor != null){
            sqlColunas += ", id_tipo_sensor ";
            sqlValores += ", ? ";
            params.add(idTipoSensor);
        } else {
            return null;
        }

        if (idEquipamento != null){
            sqlColunas += ", id_equipamento ";
            sqlValores += ", ? ";
            params.add(idEquipamento);
        }

        if (idLocal != null){
            sqlColunas += ", id_local ";
            sqlValores += ", ? ";
            params.add(idLocal);
        }

        sqlColunas += ") ";
        sqlValores += ") RETURNING id_sensor;";

        Long idSensor = jdbcTemplate.queryForObject(sqlColunas + sqlValores, params.toArray(), Long.class);

        if (idSensor != null && idSensor > 0){
            return buscarSensores(idSensor, null, null, null, null).get(0);
        }

        return null;
    }

    public SensorDTO atualizarSensor(Long idSensor, Long idTipoSensor, Long idEquipamento, Long idLocal){
        List<Object> params = new ArrayList<>();
        String sql = "UPDATE sensor SET id_sensor = ? ";
        params.add(idSensor);

        if (idTipoSensor != null){
            sql += ", id_tipo_sensor = ? ";
            params.add(idTipoSensor);
        }

        if (idEquipamento != null){
            sql += ", id_equipamento = ? ";
            params.add(idEquipamento);
        }

        if (idLocal != null){
            sql += ", id_local = ? ";
            params.add(idLocal);
        }

        sql += " WHERE id_sensor = ? RETURNING id_sensor ";
        params.add(idSensor);

        if(jdbcTemplate.queryForObject(sql, params.toArray(), Long.class) != null){
            return buscarSensores(idSensor, null, null, null, null).get(0);
        }

        return null;
    }

    public int removerSensor(Long idSensor){
        StringBuilder sql = new StringBuilder("DELETE FROM sensor WHERE id_sensor = ?");

        return jdbcTemplate.update(sql.toString(), new Object[]{idSensor});
    }

}
