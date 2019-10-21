package com.automacao.lua.repository;

import com.automacao.lua.dto.EventoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class EventoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<EventoDTO> getEventos(Long idEvento, Long idEquipamento, Integer status, Timestamp hora, String cron, Timestamp fimCron) {
        return (List<EventoDTO>) findEventos(idEvento, idEquipamento, status, hora, cron, fimCron);
    }

    /**
     * @param idEvento
     * @param idEquipamento
     * @param status
     * @param hora
     * @param cron
     * @param fimCron
     * @return
     */
    private Object findEventos(Long idEvento, Long idEquipamento, Integer status, Timestamp hora, String cron, Timestamp fimCron) {
        StringBuilder sql = new StringBuilder(" SELECT  e.*, eq.nome as nome_equipamento, l.localizacao " +
                " FROM evento e " +
                " JOIN equipamento eq ON e.id_equipamento = eq.id_equipamento " +
                " JOIN local l ON eq.id_local = l.id_local " +
                " WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (idEvento != null) {
            sql.append(" AND id_evento = ? ");
            params.add(idEvento);
        }

        if (idEquipamento != null) {
            sql.append(" AND id_equipamento = ? ");
            params.add(idEquipamento);
        }

        if (status != null) {
            sql.append(" AND status = ? ");
            params.add(status);
        }

        if (hora != null) {
            sql.append(" AND hora <= ? ");
            params.add(hora);
        }

        if (cron != null) {
            sql.append(" AND cron LIKE ? ");
            params.add(cron);
        }

        if (fimCron != null) {
            sql.append(" AND fim_cron >= ? ");
            params.add(fimCron);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper(EventoDTO.class));
    }

    public EventoDTO atualizarInfoEventos(EventoDTO evento){
        List<Object> params = new ArrayList<>();
        String sql = " UPDATE evento SET id_evento = ? ";
        params.add(evento.getIdEvento());

        CronSequenceGenerator geradorCron = new CronSequenceGenerator(evento.getCron());
        evento.setHora(new Timestamp(geradorCron.next(new Date()).getTime()));
        sql+=", hora = ? ";
        params.add(evento.getHora());

        if (evento.getStatus() != null && evento.getStatus() >= -1 && evento.getStatus() <= 1){
            sql+=", status = ? ";
            params.add(evento.getStatus());
        }

        if (evento.getIdEquipamento() != null){
            sql+=", id_equipamento = ? ";
            params.add(evento.getIdEquipamento());
        }

        if (evento.getFimCron() != null){
            sql+=", fim_cron = ? ";
            params.add(evento.getFimCron());
        }

        if (evento.getCron() != null){
            sql+=", cron = ? ";
            params.add(evento.getCron());
        }

        sql += " WHERE id_evento = ? ";
        params.add(evento.getIdEvento());
        if(jdbcTemplate.update(sql, params.toArray()) == 1){
            return ((List<EventoDTO>) findEventos(evento.getIdEvento(), null, null, null, null, null)).get(0);
        }

        return null;
    }

    /**
     * @param evento
     * @return
     * @throws Exception
     */
    public EventoDTO addEvento(EventoDTO evento) throws Exception {
        //INSERT INTO "public"."evento" ("id_evento", "id_equipamento", "status", "hora", "cron", "fim_cron") VALUES (7, 2, 1, '2019-08-01 17:51:58.343000', '0 0 1 * /* /*', '2019-08-01 17:52:10.811000')
        //StringBuilder sql1 = new StringBuilder("INSERT INTO public.evento VALUES (DEFAULT, ?, ?, ?, ?, ?) ");
        StringBuilder sql1 = new StringBuilder(" INSERT INTO public.evento (id_evento ");
        StringBuilder sql2 = new StringBuilder(" VALUES (DEFAULT ");
        List<Object> params = new ArrayList<>();

        if (evento.getIdEquipamento() != null) {
            sql1.append(", id_equipamento");
            sql2.append(", ? ");
            params.add(evento.getIdEquipamento());
        }

        if (evento.getStatus() != null) {
            sql1.append(", status");
            sql2.append(", ? ");
            params.add(evento.getStatus());
        }

        CronSequenceGenerator geradorCron = new CronSequenceGenerator(evento.getCron());
        evento.setHora(new Timestamp(geradorCron.next(new Date()).getTime()));
        sql1.append(", hora");
        sql2.append(", ? ");
        params.add(evento.getHora());

        if (evento.getCron() != null) {
            sql1.append(", cron");
            sql2.append(", ? ");
            params.add(evento.getCron());
        }

        if (evento.getFimCron() != null) {
            sql1.append(", fim_cron");
            sql2.append(", ? ");
            params.add(evento.getFimCron());
        }

        sql1.append(") ");
        sql2.append(") ");
        sql1.append(sql2.toString());

        Integer sucess = jdbcTemplate.update(sql1.toString(), params.toArray());

        if (sucess == 1) {
            return ((List<EventoDTO>) jdbcTemplate.query("SELECT * FROM evento ORDER BY id_evento DESC LIMIT 1 ", new Object[]{}, new BeanPropertyRowMapper(EventoDTO.class))).get(0);
        }

        return null;
    }

    public int updateEvento(EventoDTO evento) {
        String sql = " UPDATE evento SET hora = ? WHERE id_evento = ? ";

        return jdbcTemplate.update(sql, new Object[]{evento.getHora(), evento.getIdEvento()});
    }

    public int removerEvento(Long idEvento) {
        StringBuilder sql = new StringBuilder(" DELETE FROM evento WHERE id_evento = ? ");

        int sucess = jdbcTemplate.update(sql.toString(), new Object[]{idEvento});

        return sucess;
    }
}
