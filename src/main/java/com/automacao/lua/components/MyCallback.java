package com.automacao.lua.components;

import com.automacao.lua.model.EquipamentoStatusModel;
import com.automacao.lua.model.SensorDataModel;
import com.automacao.lua.service.EquipamentoServices;
import com.automacao.lua.service.MedicaoServices;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe responsal por "escutar" as mensagens oriundos dos equipamentos conectados e realizar determinada ação.
 */
@Component
public class MyCallback implements MqttCallback {

    @Autowired
    private MedicaoServices medicaoServices;

    @Autowired
    private EquipamentoServices equipamentoServices;

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCallback.class);

    public void connectionLost(Throwable arg0) {
        LOGGER.error("CONEXÃO PERDIDA COM O BROKER! ");
        LOGGER.error(arg0.toString());
    }

    public void deliveryComplete(IMqttDeliveryToken arg0) {
        LOGGER.info(arg0.toString());
    }

    public void messageArrived(String topic, MqttMessage message) {
        switch (topic) {
            /**
             * Nesse caso é verificado as mensagens envidas pelo sensor e é realizada a persistencia da medição.
             */
            case "sensor_data":
                try {
                    Gson gson = new Gson();
                    SensorDataModel sensorDataModel = gson.fromJson(message.toString(), SensorDataModel.class);
                    medicaoServices.persistirMedicaoSensor(sensorDataModel.getIdSensor(), sensorDataModel.getMedicao());
                } catch (JsonSyntaxException jse) {
                    LOGGER.error("Erro na formatacao da mensagem, exemplo de sintex correta: {idSensor: 1, medicao: 5.2}.");
                    LOGGER.error(jse.toString());
                } catch (Exception e) {
                    LOGGER.error(e.toString());
                }

                break;
            /**
             * Nesse caso deve ser garantido que o valor de status do equipamento no BD e o status real do equipamento (informado pelo controlador do equipamento via mensagem)
             * são o mesmo.
             *
             * A ultima mensagem enviada para esse topico será considerada como o valor real do status do equipamento.
             */
            case "equipamento_status":
                try {
                    Gson gson = new Gson();
                    EquipamentoStatusModel equipamentoStatusModel = gson.fromJson(message.toString(), EquipamentoStatusModel.class);

                    equipamentoServices.mudarStatusViaCallback(equipamentoStatusModel.getIdEquipamento(), equipamentoStatusModel.getStatus());
                } catch (JsonSyntaxException jse) {
                    LOGGER.error("Erro na formatacao da mensagem, exemplo de sintex correta: {idEquipamento: 1, status: 1}.");
                    LOGGER.error(jse.toString());
                } catch (Exception e) {
                    LOGGER.error(e.toString());
                }
                break;
            default:
                LOGGER.info("\ntopico: " + topic + "\nmensagem: " + message.toString());
                break;
        }

    }
}
