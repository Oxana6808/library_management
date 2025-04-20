package bookproject.library_management.controller;

import bookproject.library_management.service.UserService;
import bookproject.library_management.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User registeredUser = userService.registerUser(user, passwordEncoder);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

}
