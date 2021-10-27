CREATE TABLE IF NOT EXISTS role (
                                    role_id bigint NOT NULL AUTO_INCREMENT,
                                    role_code varchar(45) NOT NULL,
    role_description varchar(255) DEFAULT NULL,
    PRIMARY KEY (role_id)
    );
