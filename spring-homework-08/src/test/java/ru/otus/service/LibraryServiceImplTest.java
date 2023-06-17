package ru.otus.service;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
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
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@DataMongoTest
@Import({LibraryServiceImpl.class})
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

        author.setId("1");
        genre.setId("2");
        comment.setId("3");
        book.setId("4");
        book.setGenre(genre);
        book.setAuthor(author);
        book.setComments(new ArrayList<>(){{add(comment);}});
        addedBook.setId("5");
        Mockito.when(bookRepository.findById("4")).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.findAll()).thenReturn(new ArrayList<>(){{add(book);}});
        Mockito.when(genreRepository.findAll()).thenReturn(new ArrayList<>(){{add(genre);}});
        Mockito.when(authorRepository.findAll()).thenReturn(new ArrayList<>(){{add(author);}});
        Mockito.when(commentRepository.count()).thenReturn(1L);
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(addedBook);
        Mockito.when(bookRepository.findById("5")).thenReturn(Optional.of(addedBook));

    }

    @DisplayName("Должен вернуть книгу и проверить что у нее есть 2 комментария")
    @Test
    void getBookById() {
        Book actualBook = libraryService.getBookById("4");
        assertThat(actualBook.getComments()).hasSize(1);
    }

    @DisplayName("Должен проверить список книг")
    @Test
    void getAllBook() {
        List<Book> actualBookList = libraryService.getAllBooks();
        assertThat(actualBookList).hasSize(1);
        assertThat(actualBookList.get(0).getComments()).hasSize(1);
    }

    @DisplayName("Должен проверить добавление книги в библиотеку")
    @Test
    void addBook() {
        val addedBook = libraryService.addBook(new Book("Book for testing"));
        Book actualBook = libraryService.getBookById(addedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(addedBook);
    }
}