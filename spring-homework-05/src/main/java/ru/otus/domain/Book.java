package ru.otus.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Data
@RequiredArgsConstructor
public class Book {

    private final Long id;

    private final String name;

    private final Author author;

    private final Genre genre;

    public MapSqlParameterSource toMap() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        if (this.id != null) {
            parameters.addValue("id", this.id);
        }
        parameters.addValue("name", this.name);
        if (author != null) {
            parameters.addValue("authorid", author.getId());
        }
        if (genre != null) {
            parameters.addValue("genreid", genre.getId());
        }
        return parameters;
    }

}
