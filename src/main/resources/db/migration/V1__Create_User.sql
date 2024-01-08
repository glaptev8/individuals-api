create table users (
    id    bigserial      not null
        constraint users_id_pk
        primary key,
    email varchar(256) not null
);