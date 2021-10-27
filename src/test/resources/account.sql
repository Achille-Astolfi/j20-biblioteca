CREATE TABLE IF NOT EXISTS account (
  account_id bigint NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  full_name varchar(255) NOT NULL,
  PRIMARY KEY (account_id)
);
CREATE TABLE IF NOT EXISTS "account_role" (
  "account_id" bigint NOT NULL REFERENCES account,
  role_id bigint NOT NULL REFERENCES role,
  PRIMARY KEY ("account_id",role_id)
);
