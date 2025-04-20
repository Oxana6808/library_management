package bookproject.library_management.controller;

import bookproject.library_management.model.User;
import bookproject.library_management.service.BookService;
import bookproject.library_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import  bookproject.library_management.model.Book;

import java.util.List;

// @RestController для REST API книг
@RestController
@RequestMapping("/api/books")
public class BookController {

    // Внедрение BookService
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;


    // Добавление книги с валидацией
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(bookService.addBook(book, user));
    }

    //Получение всех книг
    @GetMapping
    public List<Book> getAllBooks() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        return bookService.getAllBooksByUser(user);
    }

    // Получение книги по ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        if (!book.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(book);
    }

    // Обновление книги
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        if (!book.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(bookService.updateBook(id, bookDetails));
    }

    // Удаление книги
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        if (!book.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
