CREATE TABLE IF NOT EXISTS book (
    book_id bigint NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    author_id bigint NOT NULL REFERENCES "author" ("author_id"),
    PRIMARY KEY (book_id)
    );
