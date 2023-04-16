package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;

public interface LibraryService {

    Book getBookById(Long id);

    List<Book> getAllBooks();

    Book addBook(Book book);

    void deleteBookById(Long id);

    void addCommentByBookId(Comment comment, Long id);

    List<Comment> getAllCommentsByBookId(Long id);

}
