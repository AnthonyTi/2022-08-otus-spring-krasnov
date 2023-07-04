package ru.otus.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.service.LibraryService;

@Controller
@AllArgsConstructor
public class PageController {

    private final LibraryService libraryService;

    @GetMapping("/books")
    public String listBooksPage(Model model) {
        return "books";
    }

    @GetMapping("/books/edit/{id}")
    public String getBookPage(@PathVariable String id, Model model) {
        model.addAttribute("book", libraryService.getBookById(id));
        model.addAttribute("authors", libraryService.getAllAuthors());
        model.addAttribute("genres", libraryService.getAllGenres());
        return "edit";
    }

    @GetMapping("/books/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book(null, new Author(), new Genre()));
        model.addAttribute("authors", libraryService.getAllAuthors());
        model.addAttribute("genres", libraryService.getAllGenres());
        return "edit";
    }

    @GetMapping("/books/{id}/comments")
    public String addComment(@PathVariable String id, Model model) {
        model.addAttribute("bookId", id);
        model.addAttribute("comment", new Comment());
        return "comments";
    }

}
