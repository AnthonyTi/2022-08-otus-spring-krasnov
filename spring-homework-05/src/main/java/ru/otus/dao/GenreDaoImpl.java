package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Genre insert(Genre genre) {
        var kh = new GeneratedKeyHolder();
        jdbcTemplate.update("insert into GENRE (name) values (:name)", genre.toMap(), kh);
        return new Genre(kh.getKey().longValue(), genre.getName());
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcTemplate.update(
                "delete from GENRE where ID = :id", params
        );
    }

    @Override
    public void update(Genre genre) {
        jdbcTemplate.update("update GENRE SET NAME = :name WHERE ID = :id", genre.toMap());
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcTemplate.queryForObject(
                "select ID, NAME from GENRE where ID = :id", params, new GenreDaoImpl.GenreMapper()
        );
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query("select ID, NAME from GENRE", new GenreDaoImpl.GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }

}
