-- DROP TABLE t_user_displayed_pool;

CREATE TABLE t_user_displayed_pool
(
  id_user integer NOT NULL,
  id_pool integer NOT NULL,
  CONSTRAINT pl_user_pool PRIMARY KEY (id_user, id_pool),
  CONSTRAINT user_pool1 FOREIGN KEY (id_pool)
      REFERENCES t_pool (id_pool) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_pool2 FOREIGN KEY (id_user)
      REFERENCES t_user (id_user) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE t_user_displayed_pool OWNER TO postgres;