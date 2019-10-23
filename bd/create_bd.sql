-- CRAIAÇÃO Database: lua
---------------------------------------------------------------------------
-- DROP DATABASE lua;

CREATE DATABASE lua
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- TABELA DE LOCAIS DO EQUIPAMENTO
---------------------------------------------------------------------------
create table local
(
	id_local serial not null
		constraint local_pkey
			primary key,
	localizacao varchar(200) not null,
	setor varchar(100),
	capacidade integer,
	descricao varchar(300)
);

comment on table local is 'Entidade de representa uma sala, auditorio ou locais de forma geral';
comment on column local.id_local is 'Identificador do local';
comment on column local.localizacao is 'Descrição da localização';
comment on column local.setor is 'Setor da localização';
comment on column local.capacidade is 'Capacidade em número de pessoas do local';
comment on column local.descricao is 'Descrição/Observações a respeito do local';


-- TABELA DE CATEGORIA
---------------------------------------------------------------------------
create table categoria
(
	id_categoria serial not null
		constraint categoria_pk
			primary key,
	nome varchar(50)
);
comment on table categoria is 'Categoria do equipamento, isto é, tipo (ar-condicionado, lampada..)';


-- TABELA DE EQUIPAMENTOS
---------------------------------------------------------------------------
create table equipamento
(
	id_equipamento serial not null
		constraint equipamento_pkey
			primary key,
	nome varchar(100) not null,
	descricao varchar(200),
	status integer default 0 not null,
	marca varchar(50),
	modelo varchar(50),
	id_local integer
		constraint equipamento_local_id_local_fk
			references local
				on update cascade,
	tombamento bigint,
	data_cadastro date,
	potencia bigint,
	id_categoria integer
		constraint equipamento_categoria_id_categoria_fk
			references categoria
				on update cascade on delete set null
);

comment on table equipamento is 'Entidade de representa um equipamento';
comment on column equipamento.nome is 'Nome do equipamento';
comment on column equipamento.descricao is 'Descrição das caracteristicas do equipamento';
comment on column equipamento.status is '-1: Defeituoso, 0: Desligado, 1: Ligado';
comment on column equipamento.marca is 'Marca do equipamento';
comment on column equipamento.modelo is 'Modelo do equipamento';
comment on column equipamento.id_local is 'Identificador da localização do equipamento';
comment on column equipamento.tombamento is 'Número de tombamento do equipamento';
comment on column equipamento.data_cadastro is 'Data do cadastro do equimento';
comment on column equipamento.potencia is 'Potência do equipamento em Watts';
comment on column equipamento.id_categoria is 'Identificador da categoria do equipamento';

-- TABELA DE EVENTOS
---------------------------------------------------------------------------
create table evento
(
	id_evento serial not null
		constraint evento_pkey
			primary key,
	id_equipamento integer not null
		constraint evento_equipamento_id_equipamento_fk
			references equipamento,
	status integer not null,
	hora timestamp(6) with time zone default now(),
	cron varchar(45),
	fim_cron timestamp(6) with time zone
);

comment on column evento.hora is 'Hora da ultima atuazação';


-- TABELA DE USUÁRIOS
---------------------------------------------------------------------------
create table if not exists usuario
(
    id_usuario serial not null
        constraint usuario_pk
            primary key,
    login varchar(20) not null,
    senha varchar(50) not null,
    nome varchar,
    permissoes varchar,
    email varchar
);

comment on column usuario.login is 'Login do usuario, deve ser unico.';
comment on column usuario.permissoes is 'Permissoes do usuario.';

alter table usuario owner to postgres;

create unique index if not exists usuarios_login_uindex on usuario (login);
create unique index if not exists usuarios_email_uindex on usuario (email);


-- TABELA DE TIPO SENSOR
---------------------------------------------------------------------------
create table tipo_sensor
(
    id_tipo_sensor serial not null
        constraint tipo_sensor_pk
            primary key,
    descricao varchar,
    unidade varchar,
    sigla_unidade varchar
);

comment on table tipo_sensor is 'Tabela que contém os diferentes tipos de sensores e seus atributos.';
comment on column tipo_sensor.unidade is 'Unidade de medida do sensor';
comment on column tipo_sensor.sigla_unidade is 'Sigla da unidade de medida.';

-- TABELA DE SENSOR
---------------------------------------------------------------------------
create table sensor
(
    id_sensor bigserial not null
        constraint sensor_pk
            primary key,
    id_tipo_sensor integer not null
        constraint sensor_tipo_sensor_id_tipo_sensor_fk
            references tipo_sensor,
    id_equipamento integer
        constraint sensor_equipamento_id_equipamento_fk
            references equipamento,
    medicao numeric,
    id_local integer
        constraint sensor_local_id_local_fk
            references local
);

comment on table sensor is 'Tabela responsavel por armazenar todos os sensores do sistema';
comment on column sensor.id_tipo_sensor is 'Identificador para o tipo de sensor.';
comment on column sensor.id_equipamento is 'Identificador do equipamento ao qual o sensor poderá está atrelado.';
comment on column sensor.medicao is 'Valor real medido (ultima medição) pelo sensor na unidade padrão do mesmo.';
comment on column sensor.id_local is 'Identificador ao qual o sensor poderá está atrelado.';

-- HISTORICO DE MEDIÇÕES
---------------------------------------------------------------------------
create table historico_medicoes
(
    id_sensor bigint not null
        constraint historico_medicoes_sensor_id_sensor_fk
            references sensor,
    hora_medicao timestamp default now(),
    medicao real
);

alter table historico_medicoes owner to postgres;

-- FUNÇÕES
---------------------------------------------------------------------------
create function sem_acento(text) returns text
	language sql
as $$
select
translate($1,'áàâãäéèêëíìïóòôõöúùûüÁÀÂÃÄÉÈÊËÍÌÏÓÒÔÕÖÚÙÛÜçÇ','aaaaaeeeeiiiooooouuuuAAAAAEEEEIIIOOOOOUUUUcC');
$$;

comment on function sem_acento(text) is 'Recebe uma String e a retorna sem acento';

--alter function sem_acento(text) owner to postgres;
