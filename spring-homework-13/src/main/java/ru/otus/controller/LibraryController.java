package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.service.LibraryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    private final LibraryService libraryService;

    @GetMapping("/api/book")
    public List<BookDto> getAllBooks() {
        logger.info("getAllBooks request");
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
    public BookDto saveBook(@RequestBody BookDto book) {
        return BookDto.toDto(libraryService.addBook(book.toDomainObject()));
    }

    @PutMapping("/api/book")
    public BookDto updateBook(@RequestBody BookDto book) {
        return BookDto.toDto(libraryService.updateBook(book.toDomainObject()));
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable("id") String id) {
        libraryService.deleteBookById(id);
    }

    @GetMapping("/api/book/comment/{id}")
    public List<CommentDto> getCommentsByBookId(@PathVariable String id) {
        return libraryService.getAllCommentsByBookId(id).stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
}

    @PostMapping("/api/book/comment/{id}")
    public void addComment(@PathVariable String id, @RequestBody CommentDto commentDto) {
        libraryService.addCommentByBookId(commentDto.toDomainObject(), id);
    }

}
