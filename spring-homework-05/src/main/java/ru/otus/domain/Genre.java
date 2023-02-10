package ru.otus.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@RequiredArgsConstructor
@Data
public class Genre {

    private final Long id;

    private final String name;

    public MapSqlParameterSource toMap() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        if (this.id != null) {
            parameters.addValue("id", this.id);
        }
        parameters.addValue("name", this.name);
        return parameters;
    }

}
