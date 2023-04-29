package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    @Override
    public Genre addGenre(Genre genre) {
        return genreRepository.insert(genre);
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.insert(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(final Long id) {
        Book book = bookRepository.getById(id);
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        List<Book> bookList = bookRepository.getAll();
        return bookList;
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.insert(book);
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        val book = bookRepository.getById(id);
        bookRepository.deleteBook(book);
    }

    @Override
    @Transactional
    public void addCommentByBookId(Comment comment, Long id) {
        val book = bookRepository.getById(id);
        comment.setBook(book);
        commentRepository.addComment(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByBookId(Long id) {
        return bookRepository.getById(id).getComments();
    }

    @Override
    public Long getCountOfComments() {
        return commentRepository.count();
    }
}
