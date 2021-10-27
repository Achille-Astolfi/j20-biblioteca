DELETE FROM "account_role";
DELETE FROM role;
DELETE FROM account;

INSERT INTO role (role_code, role_description) VALUES ('ADMIN', 'Amministatore');
INSERT INTO role (role_code, role_description) VALUES ('GUEST', 'Ospite');
INSERT INTO role (role_code, role_description) VALUES ('USER', 'Utente');

INSERT INTO account (username, full_name) VALUES ('user', 'Mario Rossi');

INSERT INTO "account_role" ("account_id", role_id) VALUES (1, 1);
