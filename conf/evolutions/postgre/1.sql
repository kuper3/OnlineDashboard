# users schema
 
# --- !Ups
 
CREATE TABLE if not exists users (
    id SERIAL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL
);
 
# --- !Downs
 
DROP TABLE users;