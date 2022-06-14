create TABLE if not exists users
(
    id       SERIAL PRIMARY KEY,
    name     varchar(250),
    email    varchar(250) unique,
    password varchar(250)
);