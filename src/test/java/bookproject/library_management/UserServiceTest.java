package bookproject.library_management;

import bookproject.library_management.model.User;
import bookproject.library_management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
    }

    @Test
    public void testRegisterUser() {
        User savedUser = userService.registerUser(user, passwordEncoder);
        assertNotNull(savedUser.getId());
        assertEquals("Test User", savedUser.getName());
        assertEquals("testuser@example.com", savedUser.getEmail());
        assertNotEquals("password123", savedUser.getPassword()); // Пароль должен быть зашифрован
    }

    @Test
    public void testRegisterUser_DuplicateEmail() {
        userService.registerUser(user, passwordEncoder);
        User duplicateUser = new User();
        duplicateUser.setName("Another User");
        duplicateUser.setEmail("testuser@example.com");
        duplicateUser.setPassword("password456");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(duplicateUser, passwordEncoder);
        });
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    public void testGetAllUsers() {
        userService.registerUser(user, passwordEncoder);
        User user2 = new User();
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password456");
        userService.registerUser(user2, passwordEncoder);

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertEquals("Test User", users.get(0).getName());
        assertEquals("User 2", users.get(1).getName());
    }

    @Test
    public void testFindByEmail() {
        userService.registerUser(user, passwordEncoder);
        User foundUser = userService.findByEmail("testuser@example.com");
        assertNotNull(foundUser);
        assertEquals("Test User", foundUser.getName());
        assertEquals("testuser@example.com", foundUser.getEmail());

        User notFoundUser = userService.findByEmail("nonexistent@example.com");
        assertNull(notFoundUser);
    }
}
