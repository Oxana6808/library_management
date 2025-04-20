package bookproject.library_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

// @Entity для маппинга на таблицу BOOK
@Entity
@Table(name = "BOOK") // Явно указываем имя таблицы
public class Book {

    // Первичный ключ с автоинкрементом
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Проверка, что название книги не пустое
    @NotBlank(message = "Title is mandatory")
    private String title;

    // Проверка, что автор не пустой
    @NotBlank(message = "Author is mandatory")
    private String author;

    // Флаг доступности книги (по умолчанию true)
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

}
