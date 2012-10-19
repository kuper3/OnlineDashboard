# my1 schema
 
# --- !Ups
CREATE TABLE IF NOT EXISTS visitors (
    id SERIAL,
    name varchar(255),
    host varchar(255) NOT NULL,
    user_agent varchar(255) NOT NULL,
    added TIMESTAMP
);



 
# --- !Downs
DROP TABLE IF EXISTS visitors;