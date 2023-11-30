package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    @Override
    @HystrixCommand(commandKey = "addGenre", groupKey = "LibraryService")
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @HystrixCommand(commandKey = "addAuthor", groupKey = "LibraryService")
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @HystrixCommand(commandKey = "getAllGenres", groupKey = "LibraryService")
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    @HystrixCommand(commandKey = "getAllAuthors", groupKey = "LibraryService")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @HystrixCommand(commandKey = "getBookById", groupKey = "LibraryService")
    public Book getBookById(final String id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    @HystrixCommand(commandKey = "getAllBooks", groupKey = "LibraryService")
    public List<Book> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList;
    }

    @Override
    @HystrixCommand(commandKey = "addBook", groupKey = "LibraryService")
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @HystrixCommand(commandKey = "updateBook", groupKey = "LibraryService")
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @HystrixCommand(commandKey = "deleteBookById", groupKey = "LibraryService")
    public void deleteBookById(String id) {
        val book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            val comments = book.getComments();
            comments.stream()
                    .forEach(comment -> commentRepository.delete(comment));
            bookRepository.delete(book);
        }

    }

    @Override
    @HystrixCommand(commandKey = "addCommentByBookId", groupKey = "LibraryService")
    public void addCommentByBookId(Comment comment, String id) {

        val book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            val commentSaved = commentRepository.save(comment);
            val comments = book.getComments();
            comments.add(comment);
            book.setComments(comments);
            bookRepository.save(book);
        }

    }

    @Override
    @HystrixCommand(commandKey = "getAllCommentsByBookId", groupKey = "LibraryService")
    public List<Comment> getAllCommentsByBookId(String id) {

        val book = bookRepository.findById(id).orElse(null);
        if (book != null){
            return book.getComments().stream()
                    .map(comment -> commentRepository.findById(comment.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }
        return null;
    }

}
