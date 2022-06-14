create TABLE if not exists candidates
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    position    varchar(200),
    skills      text,
    created_date timestamp default CURRENT_TIMESTAMP,
    city_id     int references city (id),
    user_id     int references users(id)
);

