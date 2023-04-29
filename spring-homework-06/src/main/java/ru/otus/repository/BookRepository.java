package ru.otus.repository;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;

public interface BookRepository {

    Book insert(Book book);

    void deleteBook(Book book);

    void updateNameById(Long id, String name);

    Book updateBook(Book book);

    Book getById(Long id);

    List<Book> getByName(String name);

    List<Book> getAll();

}
