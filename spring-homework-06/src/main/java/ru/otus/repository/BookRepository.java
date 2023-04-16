package ru.otus.repository;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;

public interface BookRepository {

    Book insert(Book book);

    void deleteById(Long id);

    void updateNameById(Long id, String name);

    Book getById(Long id);

    List<Book> getByName(String name);

    List<Book> getAll();

    void addCommentToBook(Comment comment, Long bookId);

}
