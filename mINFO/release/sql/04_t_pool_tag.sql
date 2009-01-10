-- Table: t_pool_tag

-- DROP TABLE t_pool_tag;

CREATE TABLE t_pool_tag
(
  id_pool integer NOT NULL,
  id_tag integer NOT NULL,
  CONSTRAINT t_pool_tag_pkey PRIMARY KEY (id_pool, id_tag),
  CONSTRAINT pool_tag1 FOREIGN KEY (id_pool)
      REFERENCES t_pool (id_pool) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT pool_tag2 FOREIGN KEY (id_tag)
      REFERENCES t_tag (id_tag) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE t_pool_tag OWNER TO postgres;