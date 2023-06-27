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
        return "edit";
    }

    @PostMapping("/books/edit")
    public String saveBook(@ModelAttribute("book") Book book,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        libraryService.updateBookNameById(book.getId(), book.getName());
        return "redirect:/books";
    }

    @GetMapping("/books/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book(null, new Author(), new Genre()));
        return "add-book";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute("book")Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-book";
        }
        libraryService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/comments")
    public String addComment(@PathVariable String id, Model model) {
        model.addAttribute("book", libraryService.getBookById(id));
        model.addAttribute("bookId", id);
        model.addAttribute("comment", new Comment());
        return "comments";
    }

    @PostMapping("/books/comments/add")
    public String addComment(@RequestParam("book_id") String id,
                             @ModelAttribute("comment") Comment comment,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "comments";
        }
        libraryService.addCommentByBookId(comment, id);
        return "redirect:/book/comments?book_id="+id;
    }

}
