package ru.otus.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.LibraryService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    private List<Book> books = List.of(
            new Book("Book 1", new Author("Author 1"), new Genre("Genre 1")),
            new Book("Book 2", new Author("Author 2"), new Genre("Genre 2")));

    private final Book book = new Book("Expected book", new Author("Author 1"), new Genre("Genre 1"));

    @Test
    void willReturnExpectedBooks() throws Exception {
        given(libraryService.getAllBooks()).willReturn(books);
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", books));
    }

    @Test
    void willReturnExpectedBook() throws Exception {
        given(libraryService.getBookById("someId")).willReturn(book);
        mvc.perform(get("/book/edit?id=someId"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", book));
    }

    @Test
    void willReturn3xxRedirect() throws Exception {
        mvc.perform(post("/book/add"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}