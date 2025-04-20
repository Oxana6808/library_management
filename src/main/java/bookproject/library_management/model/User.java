package bookproject.library_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

// @Entity указывает, что класс является сущностью, мапится на таблицу в базе данных
@Entity
@Table(name = "LIBRARY_USER") // Указываем имя таблицы
public class User {

    // @Id обозначает первичный ключ, @GeneratedValue задает стратегию автоинкремента
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank проверяет, что поле не пустое и не null
    @NotBlank(message = "Name is mandatory")
    private String name;

    // @Email проверяет, что строка является валидным email
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }

}
