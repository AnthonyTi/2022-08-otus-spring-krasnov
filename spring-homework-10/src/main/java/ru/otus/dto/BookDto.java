package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import ru.otus.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BookDto {

    @Id
    private String id;

    private String name;

    private AuthorDto author;

    private GenreDto genre;

    private List<CommentDto> comments;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName(),
                AuthorDto.toDto(book.getAuthor()),
                GenreDto.toDto(book.getGenre()),
                book.getComments().stream().map(CommentDto::toDto).collect(Collectors.toList()));
    }

    public Book toDomainObject() {
        return new Book(id, name, author.toDomainObject(), genre.toDomainObject());
    }

}
