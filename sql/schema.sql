create table if not exists nsi_okei
(
    id bigserial not null
        constraint nsi_okei_pk
            primary key,
    guid uuid not null,
    code varchar(3) not null,
    name varchar(1000) not null,
    symbol varchar(30),
    actual boolean default true,
    section_code integer,
    section_name varchar(1000),
    group_code integer,
    group_name varchar(1000),
    date_created timestamp default now() not null,
    date_modified timestamp
);


create unique index if not exists nsi_okei_guid_uindex
    on nsi_okei (guid);

create unique index if not exists nsi_okei_code_uindex
    on nsi_okei (code);

create table if not exists nsi_okfs
(
    id bigserial not null
        constraint nsi_okfs_pk
            primary key,
    guid uuid not null,
    code varchar(2) not null,
    name varchar(250),
    actual boolean default true not null,
    date_created timestamp default now() not null,
    date_modified timestamp,
    parent_code varchar(2)
);


create unique index if not exists nsi_okfs_guid_uindex
    on nsi_okfs (guid);

create unique index if not exists nsi_okfs_code_uindex
    on nsi_okfs (code);

create index if not exists nsi_okfs_parent_index
    on nsi_okfs (parent_code);

create table if not exists nsi_okopf
(
    id bigserial not null
        constraint nsi_okopf_pk
            primary key,
    guid uuid not null,
    code varchar(5),
    name varchar(250),
    actual boolean default true not null,
    date_created timestamp default now() not null,
    date_modified timestamp,
    parent_code varchar(5)
);

create unique index if not exists nsi_okopf_guid_uindex
    on nsi_okopf (guid);

create unique index if not exists nsi_okopf_code_uindex
    on nsi_okopf (code);

create index if not exists nsi_okopf_parent_index
    on nsi_okopf (parent_code);

create table if not exists nsi_okpd2
(
    id bigserial not null
        constraint nsi_okpd2_pk
            primary key,
    guid uuid not null,
    code varchar(7) not null,
    name varchar(500),
    actual boolean default true not null,
    date_created timestamp default now() not null,
    date_modified timestamp,
    parent_code varchar(7)
);

create unique index if not exists nsi_okpd2_guid_uindex
    on nsi_okpd2 (guid);

create unique index if not exists nsi_okpd2_code_uindex
    on nsi_okpd2 (code);

create index if not exists nsi_okpd2_parent_index
    on nsi_okpd2 (parent_code);

create table if not exists nsi_okato
(
    id bigserial not null
        constraint nsi_okato_pk
            primary key,
    guid uuid not null,
    code varchar(11) not null,
    name varchar(200),
    actual boolean default true not null,
    date_created timestamp default now() not null,
    date_modified timestamp,
    parent_code varchar(11)
);

create unique index if not exists nsi_okato_guid_uindex
    on nsi_okato (guid);

create unique index if not exists nsi_okato_code_uindex
    on nsi_okato (code);

create index if not exists nsi_okato_parent_index
    on nsi_okato (parent_code);

create table if not exists nsi_oktmo
(
    id bigserial not null
        constraint nsi_oktmo_pk
            primary key,
    guid uuid not null,
    code varchar(11) not null,
    name varchar(500),
    actual boolean default true not null,
    date_created timestamp default now() not null,
    date_modified timestamp,
    parent_code varchar(11),
    okato_codes jsonb
);

create unique index if not exists nsi_oktmo_guid_uindex
    on nsi_oktmo (guid);

create unique index if not exists nsi_oktmo_code_uindex
    on nsi_oktmo (code);

create index if not exists nsi_oktmo_parent_index
    on nsi_oktmo (parent_code);

create table if not exists nsi_okved2
(
    id bigserial not null
        constraint nsi_okved2_pk
            primary key,
    guid uuid not null,
    code varchar(30) not null,
    name varchar(500),
    actual boolean default true not null,
    date_created timestamp default now() not null,
    date_modified timestamp,
    section varchar(1),
    parent_code varchar(30)
);


create unique index if not exists nsi_okved2_code_uindex
    on nsi_okved2 (code);

create unique index if not exists nsi_okved2_guid_uindex
    on nsi_okved2 (guid);

create index if not exists nsi_okved2_parent_code_index
    on nsi_okved2 (parent_code);

