package ru.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.domain.Role;
import ru.otus.domain.User;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.repository.RoleRepository;
import ru.otus.repository.UserRepository;

import java.util.List;
import java.util.Set;


@ChangeLog
public class InitMongoDBDataChangeLog {

    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;

    private Comment firstComment;

    private Comment secondComment;

    private Book book;

    @ChangeSet(order = "000", id = "dropDB", author = "anthony", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "anthony", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        author1 = repository.save(new Author("Masha"));
        author2 = repository.save(new Author("Misha"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "anthony", runAlways = true)
    public void initGenres(GenreRepository repository) {
        genre1 = repository.save(new Genre("comedy"));
        genre2 = repository.save(new Genre("horror"));
    }

    @ChangeSet(order = "003", id = "initComments", author = "anthony", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
        firstComment = commentRepository.save(new Comment("Первый рандомный комментарий"));
        secondComment = commentRepository.save(new Comment("Второй более или менее внятный комментарий"));
    }

    @ChangeSet(order = "004", id = "initBooks", author = "anthony", runAlways = true)
    public void initBooks(BookRepository repository) {
        book = repository.save(new Book("Book1", author1, genre1, firstComment, secondComment));
        repository.save(new Book("Book2", author2, genre2));

    }

    @ChangeSet(order = "005", id = "initRolesAndUsers", author = "anthony", runAlways = true)
    public void initRolesAndUsers(RoleRepository roleRepository, UserRepository userRepository) {
        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ROLE_ADMIN");
        Role roleUser = new Role();
        roleUser.setRoleName("ROLE_USER");
        List<Role> rolesInserted = roleRepository.insert(List.of(roleAdmin, roleUser));

        User userAdmin = new User();
        userAdmin.setUserName("admin");
        userAdmin.setPassword("pass");
        userAdmin.setRoles(Set.of(rolesInserted.get(0)));
        User user = new User();
        user.setUserName("user");
        user.setPassword("pass");
        user.setRoles(Set.of(rolesInserted.get(1)));
        userRepository.insert(List.of(userAdmin, user));

    }

}
