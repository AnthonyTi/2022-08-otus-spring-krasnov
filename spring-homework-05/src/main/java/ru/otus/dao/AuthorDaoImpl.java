package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Author insert(Author author) {
        var kh = new GeneratedKeyHolder();
        jdbcTemplate.update(
                "insert into AUTHOR (name) values (:name)",
                author.toMap(),
                kh);
        return new Author(kh.getKey().longValue(), author.getName());
    }


    @Override
    public void update(Author author) {
        jdbcTemplate.update(
                "update AUTHOR SET NAME = :name WHERE ID = :id",
                author.toMap());
    }

    @Override
    public Author getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcTemplate.queryForObject(
                "select ID, NAME from AUTHOR where ID = :id",
                params,
                new AuthorMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        return jdbcTemplate.query(
                "select ID, NAME from AUTHOR",
                new AuthorMapper());
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcTemplate.update(
                "delete from AUTHOR where ID = :id",
                params);
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Author(id, name);
        }
    }

}
