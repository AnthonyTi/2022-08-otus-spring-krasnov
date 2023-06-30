package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/api/book")
    public Flux<BookDto> getAllBooks() {
        logger.info("getAllBooks request");
        return bookRepository.findAll()
                .map(BookDto::toDto);
    }

    @GetMapping("/api/book/{id}")
    public Mono<BookDto> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .map(BookDto::toDto);
    }

    @PostMapping("/api/book")
    public Mono<BookDto> saveBook(@RequestBody BookDto book) {
        return bookRepository.save(book.toDomainObject()).map(BookDto::toDto);
    }

    @PutMapping("/api/book")
    public Mono<BookDto> updateBook(@RequestBody BookDto book) {
        return bookRepository.save(book.toDomainObject()).map(BookDto::toDto);
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<ServerResponse> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id).then(ServerResponse.ok().build());
    }

    @GetMapping("/api/book/comment/{id}")
    public Flux<CommentDto> getCommentsByBookId(@PathVariable String id) {
        val flux = bookRepository.findById(id)
                .flatMapIterable(book -> book.getComments())
                .concatMap(comment -> commentRepository.findById(comment.getId()))
                .map(CommentDto::toDto);
        return flux;
}

    @PostMapping("/api/book/comment/{id}")
    public void addComment(@PathVariable String id, @RequestBody CommentDto commentDto) {
        val comment = commentRepository.save(commentDto.toDomainObject()).block();
        val book = bookRepository.findById(id)
                .doOnNext(b -> {
                    val commList = b.getComments();
                    commList.add(comment);
                    b.setComments(commList);
                    bookRepository.save(b).block();
                }).block();
    }

}
