package com.automacao.lua.components;

import com.automacao.lua.dto.EventoDTO;
import com.automacao.lua.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * Scheduled responsavel por manter a sincronia entre o cron e a hora de disparo do próximo evento.
 *
 * @Created by José dos Santos (josecnrn@gmail.com) on 08/09/2019
 */

@Component
@EnableScheduling
public class ScheduledEventos {

    //America/Recife
    //private static final String TIME_ZONE = "America/Sao_Paulo";

    @Autowired
    private EventoService eventoService;

    //Delay em milisegundos, 1 segundo são 1000 milisegundos.
    @Scheduled(fixedDelay = 5000)
    public void executar() {
        //TODO: Deve varrer todos eventos que o fimCron é menor que a data atual e atualizar o a hora do próximo evento
        // a partir do valor do cron

        boolean imprimirCron = false;

        Timestamp horaAtual = new Timestamp(System.currentTimeMillis());

        List<EventoDTO> eventos = eventoService.getEventos(null, null, horaAtual, null, horaAtual);
        //List<EventoDTO> eventos = eventoService.getEventos(null, null, null, null, null);
        if (imprimirCron) {
            System.out.println("São " + horaAtual);
            System.out.print("e há " + eventos.size() + " eventos à atualizar");
        }

        /** -> http://www.cronmaker.com/help/rest-api-help.html APIIII!!
         *  Cron: ("A B C D E F")
         *     A: Segundos (0 - 59).
         *     B: Minutos (0 - 59).
         *     C: Horas (0 - 23).
         *     D: Dia (1 - 31).
         *     E: Mês (1 - 12).
         *     F: Dia da semana (0 - 6)
         * Note também que nos exemplos foram usados o *, esse caracter indica que para o campo específicado qualquer valor será considerado.
         * Observação: Quando fazemos uso do "/" concatenando algum valor, indicamos o seguinte: para cada (/) X (valor numérico) repetição acione, em outras palavras, *'/
         * 1 indica que se qualquer valor do campo segundo mudar será acionado, logo, se fosse *'/2, a cada 2 vezes que
         * o campo segundo mudar de valor, será acionado
         */
        //final String cronExpression = "0 0 0/1 1/1 * *"; //a cada hora
        //0 0/1 * 1/1 * *
        //final String cronExpression = "* */1 * 1/1 * *"; //a cada 1 minuto   * 0 5 * 3 /*
        //final CronSequenceGenerator generator = new CronSequenceGenerator(cronExpression);
        //final Date nextExecutionDate = generator.next(new Date());
        for (EventoDTO evento : eventos) {
            if (imprimirCron) System.out.print("\nidEvento: " + evento.getIdEvento() + " | hora antiga: " + evento.getHora());

            if (evento.getCron() != null && ! evento.getCron().isEmpty()) {
                try{
                    CronSequenceGenerator gerador = new CronSequenceGenerator(evento.getCron());
                    evento.setHora(new Timestamp(gerador.next(new Date()).getTime()));

                    if (imprimirCron){
                        System.out.print(" -> hora nova: " + evento.getHora());
                    }

                    eventoService.updateEvento(evento);

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (imprimirCron) System.out.print("\n-------------------\n");
    }
}
