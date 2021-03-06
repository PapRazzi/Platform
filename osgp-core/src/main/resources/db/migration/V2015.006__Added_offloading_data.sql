-- Table: offloading_data

--DROP TABLE offloading_data;

CREATE TABLE offloading_data
(
  id bigserial NOT NULL,
  table_name character varying(32) NOT NULL,
  end_last_date_block timestamp without time zone NOT NULL
)
WITH (
  OIDS=FALSE
);

ALTER TABLE offloading_data OWNER TO osp_admin;

ALTER TABLE offloading_data ADD CONSTRAINT offloading_data_pkey PRIMARY KEY (id);

--inserting the necessary table names with a date in the past for the first time
INSERT INTO offloading_data(table_name, end_last_date_block) VALUES ('event', '2012-09-22 18:00:00');
