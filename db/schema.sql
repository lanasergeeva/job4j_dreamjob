create TABLE post
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT
);

create TABLE candidate
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    position varchar(200)
);

create TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT unique,
    password TEXT
);
