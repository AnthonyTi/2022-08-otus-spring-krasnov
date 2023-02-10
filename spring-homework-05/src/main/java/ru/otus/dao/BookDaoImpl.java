package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Book insert(Book book) {
        var kh = new GeneratedKeyHolder();
        jdbcTemplate.update(
                "insert into BOOK (name, authorid, genreid) values (:name, :authorid, :genreid)",
                book.toMap(), kh);
        return new Book(kh.getKey().longValue(), book.getName(), book.getAuthor(), book.getGenre());
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcTemplate.update(
                "delete from BOOK where ID = :id", params
        );
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update(
                "update BOOK SET NAME = :name, AUTHORID = :authorid, GENREID = :genreid WHERE ID = :id",
                book.toMap());
    }

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcTemplate.queryForObject(
                "select B.ID, B.NAME, AUTHORID, GENREID, A.NAME as AUTHORNAME, G.NAME as GENRENAME from BOOK B " +
                        "JOIN AUTHOR A " +
                        "ON A.ID = B.AUTHORID " +
                        "JOIN GENRE G " +
                        "ON G.ID = B.GENREID " +
                        "where B.ID = :id",
                params,
                new BookDaoImpl.BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query(
                "select B.ID, B.NAME, AUTHORID, GENREID, A.NAME as AUTHORNAME, G.NAME as GENRENAME from BOOK B " +
                "JOIN AUTHOR A " +
                "ON A.ID = B.AUTHORID " +
                "JOIN GENRE G " +
                "ON G.ID = B.GENREID", new BookDaoImpl.BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            long authorId = rs.getLong("authorid");
            String authorName = rs.getString("authorname");
            long genreId = rs.getLong("genreid");
            String genreName = rs.getString("genrename");
            return new Book(id, name, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }
}
