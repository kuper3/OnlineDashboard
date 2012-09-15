# my schema
 
# --- !Ups
 
CREATE TABLE IF NOT EXISTS users (
    id SERIAL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL
);
 
# --- !Downs
 
DROP TABLE IF EXISTS users;