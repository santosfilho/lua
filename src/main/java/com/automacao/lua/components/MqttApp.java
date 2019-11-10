package com.automacao.lua.components;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Classe responsavel por subscrever a API nos tropicos "sensor_data" e "equipamento_status". Além de enviar as mensagens MQTT oriundas da API.
 *
 * sensor_data: Tópico destinado ao envio de medicao do sensor.
 * equipamento_status: Tópico destinado a capturar as mensagens que deverão ser capturadas pelo usuario para realizar a mudanã de status do equipamento
 *
 * @author José dos Santos (josecnrn@gmail.com)
 * @since 09/11/2019
 */
@Component
public class MqttApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttApp.class);
    private static final String MQTT_SERVER_ADDRES = "tcp://localhost:1883";
    private MqttAsyncClient myClient;

    @Autowired
    private MyCallback myCallback;

    public MqttApp() {
        try {
            this.myClient = new MqttAsyncClient(MQTT_SERVER_ADDRES, "lua-api");
        } catch (MqttException e) {
            LOGGER.error("Erro ao instaciar MqttAsyncClient.");
            e.printStackTrace();
        }
    }

    @Bean
    private void subscribeTopicos() {
        try {
            myClient.setCallback(myCallback);

            IMqttToken token = myClient.connect();
            token.waitForCompletion();

            String tocicos[] = {"sensor_data", "equipamento_status", "erro"};
            int qos[] = {1,0,0};

            myClient.subscribe(tocicos, qos);
            LOGGER.info("CONECTADO AO BROKER COM SUCESSO! :D");
        } catch (MqttException me){
            LOGGER.error("Erro ao estabelecer conexão com o Broker:");
            LOGGER.error(me.toString());
        }
    }

    public boolean enviarMensagem(Long idEquipamento, int status) {
        try {
            MqttMessage mensagem = new MqttMessage();
            mensagem.setPayload(("{idEquipamento: " + idEquipamento + ", status: " + status + "}").getBytes());
            mensagem.setQos(1);

            myClient.publish("equipamento_status", mensagem);

            return true;

        } catch (MqttException me){
            LOGGER.error("Erro envio da mensagem MQTT:");
            LOGGER.error(me.toString());

            return false;
        } catch (Exception e){
            LOGGER.error(e.toString());

            return false;
        }
    }
}
