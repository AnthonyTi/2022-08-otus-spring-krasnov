package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.domain.Genre;

@Data
@AllArgsConstructor
public class GenreDto {

    private String id;

    private String name;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public Genre toDomainObject() {
        return new Genre(id, name);
    }

}
