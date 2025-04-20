package bookproject.library_management.controller;


import bookproject.library_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import bookproject.library_management.model.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// @RestController для обработки REST-запросов, возвращает JSON
@Controller
public class UserController {

    // Внедрение UserService
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Показать форму регистрации (веб-интерфейс)
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("newUser", new User());
        return "register"; // Возвращает шаблон register.html
    }

    // Обработать регистрацию через веб-форму
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.registerUser(newUser, passwordEncoder);
            model.addAttribute("success", true);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, @RequestParam(value = "logout", required = false) String logout) {
        if (logout != null) {
            model.addAttribute("logout", true);
        }
        return "login";
    }
}
