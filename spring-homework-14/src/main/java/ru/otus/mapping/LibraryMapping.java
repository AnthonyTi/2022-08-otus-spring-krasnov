package ru.otus.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.domain.h2.AuthorH2;
import ru.otus.domain.h2.BookH2;
import ru.otus.domain.h2.CommentH2;
import ru.otus.domain.h2.GenreH2;
import ru.otus.domain.mongo.Author;
import ru.otus.domain.mongo.Book;
import ru.otus.domain.mongo.Comment;
import ru.otus.domain.mongo.Genre;

import java.util.stream.Collectors;

@Component
public class LibraryMapping {

    private final Logger logger = LoggerFactory.getLogger("Batch");

    public BookH2 toBookH2(Book book) {
        var bookH2 = new BookH2(book.getName(),
                toAuthorH2(book.getAuthor()),
                toGenreH2(book.getGenre()));
        bookH2.setComments(book.getComments().stream().map(c -> toCommentH2(c)).collect(Collectors.toList()));
        return bookH2;
    }

    public AuthorH2 toAuthorH2(Author author) {
        return new AuthorH2(author.getName());
    }

    public GenreH2 toGenreH2(Genre genre) {
        return new GenreH2(genre.getName());
    }

    public CommentH2 toCommentH2(Comment comment) {
        return new CommentH2(comment.getText());
    }

}
