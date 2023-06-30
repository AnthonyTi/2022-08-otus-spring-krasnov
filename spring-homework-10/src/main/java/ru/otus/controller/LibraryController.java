package ru.otus.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.LibraryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/api/book")
    public List<BookDto> getAllBooks() {
        return libraryService.getAllBooks().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable("id") String id) {
        val book = Optional.ofNullable(
                        libraryService.getBookById(id)).orElseThrow(RuntimeException::new);
        val comments = Optional.ofNullable(
                        libraryService.getAllCommentsByBookId(book.getId())).orElseThrow(RuntimeException::new);
        return BookDto.toDto(book);
    }

    @PostMapping("/api/book")
    public String saveBook(@ModelAttribute("book") Book book,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        libraryService.updateBookNameById(book.getId(), book.getName());
        return "redirect:/books";
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable("id") String id) {
        libraryService.deleteBookById(id);
    }

    @GetMapping("/api/author")
    public List<AuthorDto> getAuthors() {
        return libraryService.getAllAuthors().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/genre")
    public List<GenreDto> getGenres() {
        return libraryService.getAllGenres().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/book/comment/{id}")
    public List<CommentDto> getCommentsByBookId(@PathVariable String id) {
        return libraryService.getAllCommentsByBookId(id).stream().map(CommentDto::toDto).collect(Collectors.toList());
    }

    @PostMapping("/api/book/comment/{id}")
    public void addComment(@PathVariable String id, @RequestBody CommentDto commentDto) {
        libraryService.addCommentByBookId(commentDto.toDomainObject(), id);
    }

}
