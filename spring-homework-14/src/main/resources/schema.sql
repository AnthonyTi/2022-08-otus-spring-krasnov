drop table if exists author CASCADE;
drop table if exists book CASCADE;
drop table if exists comment CASCADE;
drop table if exists genre CASCADE;

CREATE TABLE author (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE genre (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255)
);

CREATE TABLE book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    author_id BIGINT,
    genre_id BIGINT,
    FOREIGN KEY(author_id) REFERENCES author(id) ON DELETE CASCADE,
    FOREIGN KEY(genre_id) REFERENCES genre(id) ON DELETE CASCADE
);

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR(255),
    book_id BIGINT not null,
    FOREIGN KEY(book_id) REFERENCES book(id)
);
