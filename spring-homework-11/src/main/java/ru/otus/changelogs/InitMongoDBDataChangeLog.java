package ru.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Mono<Author> author1;
    private Mono<Author> author2;
    private Mono<Genre> genre1;
    private Mono<Genre> genre2;

    private Mono<Comment> firstComment;

    private Mono<Comment> secondComment;

    private Mono<Book> book;


    @ChangeSet(order = "001", id = "initAuthors", author = "anthony", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        author1 = repository.save(new Author("Masha"));
        author2 = repository.save(new Author("Misha"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "anthony", runAlways = true)
    public void initGenres(GenreRepository repository) {
        genre1 = repository.save(new Genre("comedy"));
        genre2 = repository.save(new Genre("horror"));
    }

    @ChangeSet(order = "003", id = "initComments", author = "anthony", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
        firstComment = commentRepository.save(new Comment("Первый рандомный комментарий"));
        secondComment = commentRepository.save(new Comment("Второй более или менее внятный комментарий"));
    }

    @ChangeSet(order = "004", id = "initBooks", author = "anthony", runAlways = true)
    public void initBooks(BookRepository repository) {
        book = repository.save(new Book("Book1", author1.block(), genre1.block(), firstComment.block(), secondComment.block()));
        repository.save(new Book("Book2", author2.block(), genre2.block()));

    }

}
