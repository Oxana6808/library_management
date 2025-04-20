package bookproject.library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bookproject.library_management.model.User;

import java.util.Optional;

// Репозиторий для сущности User, расширяет JpaRepository для CRUD-операций
public interface UserRepository extends JpaRepository<User, Long> {
    // Проверка существования пользователя по email
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
