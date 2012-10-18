# users schema

# --- !Ups
CREATE TABLE IF NOT EXISTS visitors (
    id SERIAL,
    name varchar(100),
    host varchar(255) NOT NULL,
    user_agent varchar(100) NOT NULL,
    added TIMESTAMP
);




# --- !Downs
DROP TABLE IF EXISTS visitors;