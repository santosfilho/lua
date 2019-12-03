# API LUA
Uma API RESTful para sistema e aplicações de IoT baseando-se em soluções existentes de código
aberto, possibilitando que sistemas possam se comunicar com equipamentos e objetos
de forma mais efetiva, facilitando o desenvolvimento de soluções de aplicações para
gerenciamento dos mesmos.

Com a API Lua é possível conectar microcontroladores que utilizam o protocolo de comunicação MQTT com
aplicações que utilizam HTTP.

## Dependencias

- OpenJDK 12 \
Pode ser encontrado no link: <https://adoptopenjdk.net/archive.html?variant=openjdk12&jvmVariant=hotspot>

- Broker MQTT \
É recomendata a utilização do MOSQUITTO BROKER, link: <https://mosquitto.org/download/>

- TimescaleDB \
Pode ser instalado seguindo os passos descitos em: <https://docs.timescale.com/latest/getting-started/installation> 

## Configurações

Tendo instalado o TimescaleDB deve ser executado o script SQL localizado no projeto em "/lua/BD/create_bd.sql". Com ele 
as tabelas, suas respectivas relações e regras serão criadas.

A API está configurada para funcionar na porta padrão 8079.

### Variáveis de ambiente

#### Banco de Dados
Variável|Descrição|Exemplo
-------|------|-----
DB_LUA_URL|Endereço do BD|jdbc:postgresql://localhost:5432/lua
DB_LUA_USER|Usuário do BD|postgres
DB_LUA_PASS|Senha do usuário do BD|senha

#### Servidor de Email
Variável|Descrição|Exemplo
-------|------|-----
MAIL_HOST|Endereço do servidor de e-mail (SMTP)|smtp.gmail.com
MAIL_USERNAME|E-mail do sistema|sconee.lua
MAIL_PASSWORD|Senha do E-mail sistema|senha

#### Broker MQTT
Variável|Descrição|Exemplo
-------|------|-----
MQTT_PUBLISHER_ID|Identificador do publisher|spring-server
MQTT_SERVER_ADDRESS|Endereço do servidor|tcp://localhost:1883
MQTT_USERNAME|Usuario do sistema ao Broker|admin
MQTT_PASSWORD|Senha do usuario do sistema ao Broker|senha_mqtt

## Utilização
Será descrito os aspectos relativos a utilização da API.

### Padrões das mensagens MQTT
<strong><em>"sensor_data"</em></strong>: Tópico destinado ao envio de medicao do sensor.
 
Exemplo de mensagem com sintex correta:\
<em>
{\
&ensp;"idSensor": 1,  
&ensp;"medicao": 5.2 \
}
</em>

<strong><em>"equipamento_status"</em></strong>: Tópico destinado a capturar as mensagens que deverão ser capturadas pelo usuario para realizar a mudanã de status do equipamento. 

Exemplo de mensagem com sintex correta:\
<em>
{\
&ensp;"idEquipamento": 1,  
&ensp;"status": 1 \
}
</em>

### Documentação Swagger
Após execução do projeto a documentação pode ser encontrada acessando: <em>http://localhost:8079/v0.1/swagger-ui.html</em>

### Projeto que utiliza a API
Sistema de Controle de Equipamentos Elétricos (SCONEE): <https://github.com/santosfilho/sconee>