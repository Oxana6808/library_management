package bookproject.library_management;

import bookproject.library_management.model.Book;
import bookproject.library_management.model.User;
import bookproject.library_management.service.BookService;
import bookproject.library_management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    private User user;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
        user = userService.registerUser(user, passwordEncoder);
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setAvailable(true);

        Book savedBook = bookService.addBook(book, user);
        assertNotNull(savedBook.getId());
        assertEquals("Test Book", savedBook.getTitle());
        assertEquals("Test Author", savedBook.getAuthor());
        assertTrue(savedBook.isAvailable());
        assertEquals(user.getId(), savedBook.getUser().getId());
    }

    @Test
    public void testGetAllBooksByUser() {
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setAvailable(true);
        bookService.addBook(book1, user);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setAvailable(false);
        bookService.addBook(book2, user);

        List<Book> books = bookService.getAllBooksByUser(user);
        assertEquals(2, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }

    @Test
    public void testGetBookById() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setAvailable(true);
        Book savedBook = bookService.addBook(book, user);

        Optional<Book> foundBook = bookService.getBookById(savedBook.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getTitle());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book();
        book.setTitle("Old Title");
        book.setAuthor("Old Author");
        book.setAvailable(true);
        Book savedBook = bookService.addBook(book, user);

        Book updatedDetails = new Book();
        updatedDetails.setTitle("New Title");
        updatedDetails.setAuthor("New Author");
        updatedDetails.setAvailable(false);

        Book updatedBook = bookService.updateBook(savedBook.getId(), updatedDetails);
        assertEquals("New Title", updatedBook.getTitle());
        assertEquals("New Author", updatedBook.getAuthor());
        assertFalse(updatedBook.isAvailable());
        assertEquals(user.getId(), updatedBook.getUser().getId());
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setAvailable(true);
        Book savedBook = bookService.addBook(book, user);

        bookService.deleteBook(savedBook.getId());
        Optional<Book> deletedBook = bookService.getBookById(savedBook.getId());
        assertFalse(deletedBook.isPresent());
    }
}
