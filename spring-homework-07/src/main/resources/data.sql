insert into author (`name`) values ('masha');
insert into author (`name`) values ('misha');
insert into genre (`name`) values ('comedy');
insert into genre (`name`) values ('horror');
insert into book (`name`, `author_id`, `genre_id`) values ('Book1', '1', '1');
insert into book (`name`, `author_id`, `genre_id`) values ('Book2', '2', '2');
insert into comment (`text`, `book_id`) values ('Good book  excellent genre', '1');
insert into comment (`text`, `book_id`) values ('Perfect', '1');
insert into comment (`text`, `book_id`) values ('Not good enough', '2');
insert into comment (`text`, `book_id`) values ('Nah!', '2');
