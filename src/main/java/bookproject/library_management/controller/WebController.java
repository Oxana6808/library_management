package bookproject.library_management.controller;


import bookproject.library_management.model.User;
import bookproject.library_management.service.BookService;
import bookproject.library_management.model.Book;
import bookproject.library_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;


// @Controller для обработки MVC-запросов, возвращающих Thymeleaf-шаблоны
@Controller
public class WebController {

    // Внедрение BookService
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

        // Отображение главной страницы со списком книг и фирмой
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        model.addAttribute("books", bookService.getAllBooksByUser(user));
        model.addAttribute("newBook", new Book());
        return "dashboard";
    }

    // Обработка добавления книги через форму
    @PostMapping("/addBook")
    public String addBook(@Valid @ModelAttribute("newBook") Book newBook, BindingResult result, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.getAllBooksByUser(user));
            return "dashboard";
        }
        bookService.addBook(newBook, user);
        return "redirect:/dashboard";
    }

    @GetMapping("/editBook/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        if (!book.getUser().getId().equals(user.getId())) {
            return "redirect:/dashboard"; // Пользователь может редактировать только свои книги
        }
        model.addAttribute("book", book);
        model.addAttribute("books", bookService.getAllBooksByUser(user));
        return "editBook";
    }

    @PostMapping("/updateBook/{id}")
    public String updateBook(@PathVariable("id") Long id, @Valid @ModelAttribute("book") Book book, BindingResult result, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        Book existingBook = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        if (!existingBook.getUser().getId().equals(user.getId())) {
            return "redirect:/dashboard";
        }
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.getAllBooksByUser(user));
            return "editBook";
        }
        bookService.updateBook(id, book);
        return "redirect:/dashboard";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        if (!book.getUser().getId().equals(user.getId())) {
            return "redirect:/dashboard";
        }
        bookService.deleteBook(id);
        return "redirect:/dashboard";
    }
}
