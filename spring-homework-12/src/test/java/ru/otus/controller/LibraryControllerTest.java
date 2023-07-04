package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.LibraryService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(
        username = "admin",
        authorities = {"ROLE_ADMIN"}
)
@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    private final Author AUTHOR = new Author("Author 1");
    private final Genre GENRE = new Genre("Genre 1");
    private List<Book> BOOKS = List.of(
            new Book("Book 1", new Author("Author 1"), new Genre("Genre 1")),
            new Book("Book 2", new Author("Author 2"), new Genre("Genre 2")));

    private final Book BOOK = new Book("Expected book", AUTHOR, GENRE);


    @Test
    @DisplayName("Должен выполнить корректное получение списка книг")
    void shouldReturnListOfBooks () throws Exception {
        when(libraryService.getAllBooks()).thenReturn(BOOKS);
        mvc.perform(get("/api/book/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(libraryService, times(1)).getAllBooks();
    }

    @Test
    @DisplayName("Должен выполнить корректное изменение книги")
    public void shouldImplementCorrectEdit() throws Exception {
        when(libraryService.getBookById(Mockito.anyString())).thenReturn(BOOK);
        when(libraryService.getAllAuthors()).thenReturn(List.of(AUTHOR));
        when(libraryService.getAllGenres()).thenReturn(List.of(GENRE));
        this.mvc.perform(get("/api/book/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(libraryService, times(1)).getBookById(anyString());
    }

    @Test
    @DisplayName("Должен выполнить корректное удаление книги")
    public void shouldImplementCorrectRemove() throws Exception {
        when (libraryService.getBookById("1")).thenReturn(BOOK);
        this.mvc.perform(delete("/api/book/1"))
                .andExpect(status().isOk());
        verify(libraryService, times(1)).deleteBookById(anyString());
    }

    @Test
    @DisplayName("Должен вернуть RuntimeException")
    void shouldReturnRuntimeException() {
        when(libraryService.getBookById("3")).thenReturn(null);
        assertThatThrownBy(() -> mvc.perform(get("/api/book/3")))
                .hasCause(new RuntimeException());
        verify(libraryService, times(1)).getBookById(anyString());
    }

}