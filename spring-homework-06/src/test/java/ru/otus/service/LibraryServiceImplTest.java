package ru.otus.service;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.BookRepositoryJpa;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Import({LibraryServiceImpl.class, BookRepositoryJpa.class})
class LibraryServiceImplTest {

    private LibraryServiceImpl libraryService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private CommentRepository commentRepository;

    @BeforeEach
    void init() {
        libraryService = new LibraryServiceImpl(bookRepository, authorRepository, genreRepository, commentRepository);

        Author author = new Author("Ivan");
        Genre genre = new Genre("Science");
        Book book = new Book("Spring Boot");
        Comment comment = new Comment("Best book ever");

        Book addedBook = new Book("Java EE");

        author.setId(1L);
        genre.setId(1L);
        comment.setId(1L);
        book.setId(1L);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setComments(new ArrayList<>(){{add(comment);}});
        addedBook.setId(2L);
        Mockito.when(bookRepository.getById(1L)).thenReturn(book);
        Mockito.when(bookRepository.getAll()).thenReturn(new ArrayList<>(){{add(book);}});
        Mockito.when(genreRepository.getAll()).thenReturn(new ArrayList<>(){{add(genre);}});
        Mockito.when(authorRepository.getAll()).thenReturn(new ArrayList<>(){{add(author);}});
        Mockito.when(commentRepository.count()).thenReturn(1L);
        Mockito.when(bookRepository.insert(any(Book.class))).thenReturn(addedBook);
        Mockito.when(bookRepository.getById(2L)).thenReturn(addedBook);

    }

    @DisplayName("Должен вернуть книгу и проверить что у нее есть 2 комментария")
    @Test
    @Transactional
    void getBookById() {
        Book actualBook = libraryService.getBookById(1L);
        assertThat(actualBook.getComments()).hasSize(1);
    }

    @DisplayName("Должен проверить список книг")
    @Test
    @Transactional
    void getAllBook() {
        List<Book> actualBookList = libraryService.getAllBooks();
        assertThat(actualBookList).hasSize(1);
        assertThat(actualBookList.get(0).getComments()).hasSize(1);
    }

    @DisplayName("Должен проверить добавление книги в библиотеку")
    @Test
    @Transactional
    void addBook() {
        val addedBook = libraryService.addBook(new Book("Book for testing"));
        Book actualBook = libraryService.getBookById(addedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(addedBook);
    }
}