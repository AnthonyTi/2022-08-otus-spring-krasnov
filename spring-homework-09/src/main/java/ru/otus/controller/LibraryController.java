package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.service.LibraryService;

@Controller
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        val books = libraryService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book/edit")
    public String getBook(@RequestParam("id") String id, Model model) {
        val book = libraryService.getBookById(id);
        val comments = libraryService.getAllCommentsByBookId(book.getId());
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "edit";
    }

    @PostMapping("/book/edit")
    public String saveBook(@ModelAttribute("book") Book book,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        libraryService.updateBookNameById(book.getId(), book.getName());
        return "redirect:/books";
    }

    @GetMapping("/book/delete")
    public String deleteBook(@RequestParam("id") String id, Model model) {
        libraryService.deleteBookById(id);
        return "redirect:/books";
    }

    @GetMapping("/book/add")
    public String addBook(Model model) {
        model.addAttribute("authors", libraryService.getAllAuthors());
        model.addAttribute("genres", libraryService.getAllGenres());
        model.addAttribute("book", new Book(null, new Author(), new Genre()));
        return "add-book";
    }

    @PostMapping("/book/add")
    public String addBook(@ModelAttribute("book")Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-book";
        }
        libraryService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/book/comments")
    public String addComment(@RequestParam("book_id") String id, Model model) {
        model.addAttribute("book", libraryService.getBookById(id));
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", libraryService.getAllCommentsByBookId(id));
        return "comments";
    }

    @PostMapping("/book/comments/add")
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
