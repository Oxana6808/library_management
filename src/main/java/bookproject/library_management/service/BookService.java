package bookproject.library_management.service;

import bookproject.library_management.model.Book;
import bookproject.library_management.model.User;
import bookproject.library_management.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service для бина с бизнес-логикой книг
@Service
public class BookService {

    // Внедрение BookRepository
    @Autowired
    private BookRepository bookRepository;

    // @Transactional для добавления книги
    @Transactional
    public Book addBook(Book book, User user) {
        book.setUser(user);
        return bookRepository.save(book);
    }
    // Получение всех книг
    public List<Book> getAllBooksByUser(User user) {
        return bookRepository.findByUser(user);
    }

    // Получение книги по ID
    public Optional<Book> getBookById(Long id){
        return bookRepository.findById(id);
    }

    // @Transactional для обновления книги
    @Transactional
    public Book updateBook(Long id, Book bookDetails){
        // Проверка существования книги
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setAvailable(bookDetails.isAvailable());
        return bookRepository.save(book);
    }

    // @Transactional для удаления книги
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

}
