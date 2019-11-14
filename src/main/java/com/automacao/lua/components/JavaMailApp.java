package com.automacao.lua.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author José dos Santos Filho (josecnrn@gmail.com)
 * @since 18/10/2019
 */
@Component
public class JavaMailApp {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(JavaMailApp.class);

    public void enviarEmail(List<String> emails, String titulo, String mensagem) {
        SimpleMailMessage msg = new SimpleMailMessage();

        if (emails != null && emails.size() > 0) {
            try {
                String[] destinatarios = new String[emails.size()];

                for (int i = 0; i < emails.size(); i++) {
                    destinatarios[i] = emails.get(i);
                }

                msg.setTo(destinatarios);
                msg.setSubject(titulo);
                msg.setText(mensagem);

                javaMailSender.send(msg);
                logger.info("Email enviado para: " + emails.toString());

            } catch (Exception e) {
                logger.info("Erro ao enviar emails para: " + emails.toString());
                e.printStackTrace();
            }
        }
    }
}