insert into author (`name`)
values ('Ivan'), ('John');

insert into genre (`name`)
values ('Science'), ('Comedy');

insert into book (`name`, `author_id`, `genre_id`)
values ('Book', '1', '1'), ('Book bad', '2', '2');

insert into comment (`text`, `book_id`)
values ('Good book  excellent genre', '1'),
       ('Perfect', '1'),
       ('Not good enough', '2'),
       ('Nah!', '2');
