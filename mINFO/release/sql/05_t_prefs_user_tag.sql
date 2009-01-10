-- Table: t_prefs_user_tag

-- DROP TABLE t_prefs_user_tag;

CREATE TABLE t_prefs_user_tag
(
  id_user integer NOT NULL,
  id_tag integer NOT NULL,
  part double precision,
  CONSTRAINT t_prefs_user_tag_pkey PRIMARY KEY (id_user, id_tag),
  CONSTRAINT t_prefs_user_tag_id_tag_fkey FOREIGN KEY (id_tag)
      REFERENCES t_tag (id_tag) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT t_prefs_user_tag_id_user_fkey FOREIGN KEY (id_user)
      REFERENCES t_user (id_user) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE t_prefs_user_tag OWNER TO postgres;