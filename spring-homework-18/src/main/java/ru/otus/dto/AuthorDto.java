package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.domain.Author;

@Data
@AllArgsConstructor
public class AuthorDto {

    private String id;

    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }

    public Author toDomainObject() {
        return new Author(id, name);
    }

}
