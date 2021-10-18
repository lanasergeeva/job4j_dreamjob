create TABLE post
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    create_date timestamp default localtimestamp,
);

create TABLE candidate
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    position    varchar(200),
    create_date timestamp default localtimestamp,
    city_id     int references city (id)
);

create TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT unique,
    password TEXT
);

create table city
(
    id   serial primary key,
    name varchar(100)
);

insert into city (name) values('Алупка');
insert into city (name) values('Симферополь');
insert into city (name) values('Севастополь');
insert into city (name) values('Краснодар');
insert into city (name) values('Керчь');
