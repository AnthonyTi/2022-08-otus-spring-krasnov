package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.service.LibraryService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AppShellCommands {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;
    private final LibraryService libraryService;


    @ShellMethod(value = "Show authors", key = "sa")
    public String showAuthors () {
        List<Author> authorList = authorDao.getAll();
        return String.format("List of authors: %s", authorList.toString());
    }

    @ShellMethod(value = "Add author", key = "addA")
    public String addAuthor (@ShellOption()String name) {
        Author author = new Author(null, name);
        author = authorDao.insert(author);
        return String.format("Author was added with id = %s", author.getId());
    }

    @ShellMethod(value = "Update author", key = "updA")
    public String updAuthor (@ShellOption()Long id, @ShellOption()String name) {
        Author author = new Author(id, name);
        authorDao.update(author);
        return String.format("Author was updated with id = %s", id);
    }

    @ShellMethod(value = "Get books", key = "getAB")
    public String getBooks () {
        List<Book> bookList = libraryService.getAllBooks();
        return String.format("List of books: %s", bookList.toString());
    }

    @ShellMethod(value = "Get books", key = "getB")
    public String getBookById (@ShellOption() Long id) {
        Book book = libraryService.getBookById(id);
        return String.format("Book: %s", book);
    }

}
