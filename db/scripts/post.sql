create TABLE if not exists post
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created_date timestamp default CURRENT_TIMESTAMP,
    city_id     int references city (id),
    user_id     int references users(id)
);



