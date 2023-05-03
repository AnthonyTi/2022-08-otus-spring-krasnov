package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.List;

public interface LibraryService {

    Genre addGenre(Genre genre);

    Author addAuthor(Author author);

    List<Genre> getAllGenres();

    List<Author> getAllAuthors();

    Book getBookById(Long id);

    List<Book> getAllBooks();

    Book addBook(Book book);

    void updateBookNameById(Long id, String name);

    void deleteBookById(Long id);

    void addCommentByBookId(Comment comment, Long id);

    List<Comment> getAllCommentsByBookId(Long id);

    Long getCountOfComments();

}
