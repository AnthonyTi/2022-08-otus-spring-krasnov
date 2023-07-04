package ru.otus.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

@Controller
@AllArgsConstructor
public class PageController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping("/books")
    public String listBooksPage(Model model) {
        return "books";
    }

    @GetMapping("/books/edit/{id}")
    public String getBookPage(@PathVariable String id, Model model) {
        model.addAttribute("book", bookRepository.findById(id).block());
        model.addAttribute("authors", authorRepository.findAll().collectList().block());
        model.addAttribute("genres", genreRepository.findAll().collectList().block());
        return "edit";
    }

    @GetMapping("/books/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book(null, new Author(), new Genre()));
        model.addAttribute("authors", authorRepository.findAll().collectList().block());
        model.addAttribute("genres", genreRepository.findAll().collectList().block());
        return "edit";
    }


    @GetMapping("/books/{id}/comments")
    public String addComment(@PathVariable String id, Model model) {
        model.addAttribute("bookId", id);
        model.addAttribute("comment", new Comment());
        return "comments";
    }

}
