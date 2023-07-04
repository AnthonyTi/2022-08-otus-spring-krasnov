package ru.otus.controller;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    private final Author AUTHOR = new Author("Author 1");
    private final Genre GENRE = new Genre("Genre 1");
    private List<Book> BOOKS = List.of(
            new Book("Book 1", new Author("Author 1"), new Genre("Genre 1")),
            new Book("Book 2", new Author("Author 2"), new Genre("Genre 2")));

    private final Book BOOK = new Book("Expected book", AUTHOR, GENRE);

    @BeforeEach
    public void beforeEach() {
        when(bookRepository.findById(anyString())).thenReturn(Mono.just(BOOK));
        when(bookRepository.save(any())).thenReturn(Mono.just(BOOK));
        when(bookRepository.findAll()).thenReturn(Flux.fromIterable(BOOKS));
        when(bookRepository.deleteById(anyString())).thenReturn(ServerResponse.ok().build().then());
    }


    @Test
    @DisplayName("Должен выполнить корректное получение списка книг")
    void shouldReturnListOfBooks () throws Exception {
        val webGet = webClient.get()
                .uri("/api/book/")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class);
        val actualList = webGet.getResponseBody().collectList().block();
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(2, actualList.size());
    }

    @Test
    @DisplayName("Должен выполнить корректное изменение книги")
    public void shouldImplementCorrectEdit() throws Exception {

        webClient.put()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(BOOK))
                .exchange()
                .expectStatus()
                .isOk();
        verify(bookRepository, times(1)).save(BOOK);
    }

    @Test
    @DisplayName("Должен выполнить корректное удаление книги")
    public void shouldImplementCorrectRemove() throws Exception {
        webClient.delete()
                .uri("/api/book/1")
                .exchange()
                .expectStatus().isOk();
        verify(bookRepository, times(1)).deleteById("1");
    }

    @Test
    @DisplayName("Должен вернуть книгу")
    void shouldReturnRuntimeException() {
        var resultGet = webClient.get()
                .uri("/api/book/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class);
        val actualBook = resultGet.getResponseBody().blockFirst();
        Assertions.assertNotNull(actualBook);
        Assertions.assertEquals(BOOK.getName(), actualBook.getName());
    }

}