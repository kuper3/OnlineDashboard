# my1 schema
 
# --- !Ups
CREATE TABLE IF NOT EXISTS visitors (
    id SERIAL,
    name varchar(100),
    host varchar(25) NOT NULL,
    user_agent varchar(100) NOT NULL,
    added TIMESTAMP
);

CREATE OR REPLACE FUNCTION trigger_visitors_set_current_time() RETURNS TRIGGER AS $$
BEGIN
    UPDATE visitors SET added=current_timestamp WHERE id=new.id;
END;
$$ LANGUAGE 'plpgsql';



CREATE TRIGGER trigger_visitors_after_upd
AFTER INSERT ON visitors EXECUTE PROCEDURE trigger_visitors_set_current_time();


 
# --- !Downs
DROP TRIGGER IF EXISTS trigger_visitors_after_upd ON visitors;

DROP FUNCTION IF EXISTS trigger_visitors_set_current_time();

DROP TABLE IF EXISTS visitors;