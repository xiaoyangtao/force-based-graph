-- Table: t_pool

-- DROP TABLE t_pool;

CREATE TABLE t_pool
(
  id_pool integer NOT NULL,
  question text,
  CONSTRAINT key_pool PRIMARY KEY (id_pool)
)
WITH (OIDS=FALSE);
ALTER TABLE t_pool OWNER TO postgres;
GRANT ALL ON TABLE t_pool TO postgres;

-- Table: t_user

-- DROP TABLE t_user;

CREATE TABLE t_user
(
  id_user integer NOT NULL,
  username character varying,
  "password" character varying,
  CONSTRAINT key_user PRIMARY KEY (id_user)
)
WITH (OIDS=FALSE);
ALTER TABLE t_user OWNER TO postgres;


-- Table: t_answer

-- DROP TABLE t_answer;

CREATE TABLE t_answer
(
  id_answer integer NOT NULL,
  answer text,
  id_pool integer NOT NULL,
  CONSTRAINT t_answer_pkey PRIMARY KEY (id_answer),
  CONSTRAINT t_answer_fkey_pool FOREIGN KEY (id_pool)
      REFERENCES t_pool (id_pool) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE t_answer OWNER TO postgres;

-- Index: fki_t_answer_fkey_pool

-- DROP INDEX fki_t_answer_fkey_pool;

CREATE INDEX fki_t_answer_fkey_pool
  ON t_answer
  USING btree
  (id_pool);



-- Table: t_user_answer

-- DROP TABLE t_user_answer;

CREATE TABLE t_user_answer
(
  id_user integer NOT NULL,
  id_answer integer NOT NULL,
  CONSTRAINT t_user_answer_pkey PRIMARY KEY (id_user, id_answer),
  CONSTRAINT t_user_answer_id_answer_fkey FOREIGN KEY (id_answer)
      REFERENCES t_answer (id_answer) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT t_user_answer_user_id FOREIGN KEY (id_user)
      REFERENCES t_user (id_user) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE t_user_answer OWNER TO postgres;

-- Index: fki_

-- DROP INDEX fki_;

CREATE INDEX fki_
  ON t_user_answer
  USING btree
  (id_answer);

-- Index: fki_t_user_answer_user_id

-- DROP INDEX fki_t_user_answer_user_id;

CREATE INDEX fki_t_user_answer_user_id
  ON t_user_answer
  USING btree
  (id_user);

