package com.automacao.lua.components;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.automacao.lua.dto.EventoDTO;
import com.automacao.lua.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * Scheduled responsavel por manter a sincronia entre o cron e a hora de disparo do próximo evento,
 * além de verificar a ação dos alarmes.
 *
 * @Created by José dos Santos (josecnrn@gmail.com) on 08/09/2019
 */

@Component
@EnableScheduling
public class ScheduledEventos {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private JavaMailApp javaMailApp;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledEventos.class);

    //Delay em milisegundos, 1 segundo são 1000 milisegundos.
    @Scheduled(fixedDelay = 1000)
    public void executar() throws MqttException {

        Timestamp horaAtual = new Timestamp(System.currentTimeMillis());
        List<EventoDTO> eventos = eventoService.getEventos(null, null, horaAtual, null, horaAtual);

        executarEventos(eventos);
        //escutarMedicoes();
    }

    /**
     * Função responsavel por executar os eventos, atualizar eles e enviar notificação ao administrador
     * caso aquele evento esteja para terminar.
     *
     * @param eventos Eventos que devem ser executados
     */
    private void executarEventos(List<EventoDTO> eventos){
        boolean imprimirCron = true && (eventos.size() > 0);

        if (imprimirCron) {
            logger.info("e há " + eventos.size() + " eventos à atualizar");
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
        for (EventoDTO evento : eventos) {
            if (imprimirCron) logger.info("idEvento: " + evento.getIdEvento() + " | hora antiga: " + evento.getHora());

            if (evento.getCron() != null && !evento.getCron().isEmpty() ) {
                try {
                    CronSequenceGenerator gerador = new CronSequenceGenerator(evento.getCron());

                    evento.setHora(new Timestamp(gerador.next(new Date()).getTime()));

                    if (imprimirCron) {
                        logger.info(" -> hora nova: " + evento.getHora());
                    }

                    eventoService.executarEvento(evento);

                    // Verifica se aquela é penultima execução
                    if (evento.getFimCron() != null && gerador.next(evento.getHora()).getTime() > evento.getFimCron().getTime()) {
                        List<String> emails = new ArrayList<>();
                        emails.add("josefilhocnrn@gmail.com");

                        StringBuilder mensagem = new StringBuilder();
                        mensagem.append("Equipamento: " + evento.getNomeEquipamento());
                        mensagem.append(".\nVai " + (evento.getStatus() == 1 ? "LIGAR" : "DESLIGAR"));
                        mensagem.append(" pela ultima vez em " + evento.getHora().toString());
                        mensagem.append("\n Caso queira que o evento não pare de ocorrer atualize o tempo de fim do evento");

                        javaMailApp.enviarEmail(emails, evento.getNomeEquipamento() + ": Evento chegando ao fim.", mensagem.toString());

                    } else if (evento.getFimCron() != null && (evento.getHora().getTime() > evento.getFimCron().getTime())) {
                        //Remove o evento caso seja sua ultima execução
                        eventoService.removerEvento(evento.getIdEvento());
                    }

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (imprimirCron) logger.info("-------------------\n");
    }
}
