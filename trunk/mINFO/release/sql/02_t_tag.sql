-- Table: t_tag

-- DROP TABLE t_tag;

CREATE TABLE t_tag
(
  id_tag integer NOT NULL,
  name character varying NOT NULL,
  CONSTRAINT t_tag_pkey PRIMARY KEY (id_tag)
)
WITH (OIDS=FALSE);
ALTER TABLE t_tag OWNER TO postgres;
