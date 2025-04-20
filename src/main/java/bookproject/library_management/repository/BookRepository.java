package bookproject.library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bookproject.library_management.model.Book;
import bookproject.library_management.model.User;

import java.util.List;

// Репозиторий для сущности Book
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByUser(User user);
}
