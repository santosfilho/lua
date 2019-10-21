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


-- CRIAÇÃO DA SEQUENCIA
---------------------------------------------------------------------------
create sequence categoria_id_categoria_seq
	as integer
	maxvalue 2147483647;

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
	id_categoria integer default nextval('categoria_id_categoria_seq'::regclass) not null
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

create sequence usuario_id_usuario_seq as integer;
alter sequence usuario_id_usuario_seq owner to postgres;

create table usuarios
(
    id_usuario integer default nextval('usuario_id_usuario_seq'::regclass) not null
        constraint usuarios_pk
            primary key,
    login varchar(20) not null,
    senha varchar(50) not null,
    nome varchar,
    permissoes varchar,
    email varchar
);

comment on column usuarios.login is 'Login do usuario, deve ser unico.';
comment on column usuarios.permissoes is 'Permissoes do usuario.';

alter table usuarios owner to postgres;

create unique index usuarios_login_uindex on usuarios (login);
create unique index usuarios_email_uindex on usuarios (email);

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
