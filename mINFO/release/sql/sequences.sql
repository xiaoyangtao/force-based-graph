-- Sequence: seq_user

-- DROP SEQUENCE seq_user;

CREATE SEQUENCE seq_user
  INCREMENT 1
  MINVALUE 1;
ALTER TABLE seq_user OWNER TO postgres;


-- Sequence: seq_answer

-- DROP SEQUENCE seq_answer;

CREATE SEQUENCE seq_answer
  INCREMENT 1
  MINVALUE 1;
ALTER TABLE seq_answer OWNER TO postgres;



-- Sequence: seq_pool

-- DROP SEQUENCE seq_pool;

CREATE SEQUENCE seq_pool
  INCREMENT 1
  MINVALUE 1;
ALTER TABLE seq_pool OWNER TO postgres;


