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

    @Transactional
    @Override
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional
    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(final Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList;
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBookNameById(Long id, String name) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setName(name);
            bookRepository.save(book);
        }
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addCommentByBookId(Comment comment, Long id) {
        val book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            comment.setBook(book);
            commentRepository.save(comment);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByBookId(Long id) {
        val book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            return commentRepository.findAllByBook(book);
        }
        return null;
    }

    @Override
    public Long getCountOfComments() {
        return commentRepository.count();
    }
}
