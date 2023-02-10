package ru.otus.dao;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

public interface BookDao {

    Book insert(Book book);

    void deleteById(Long id);

    void update(Book book);

    Book getById(Long id);

    List<Book> getAll();

}
