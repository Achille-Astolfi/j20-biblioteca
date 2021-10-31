CREATE TABLE IF NOT EXISTS author (
  author_id bigint NOT NULL AUTO_INCREMENT,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) NOT NULL,
  PRIMARY KEY (author_id)
);
