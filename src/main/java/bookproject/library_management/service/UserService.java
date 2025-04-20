package bookproject.library_management.service;

import bookproject.library_management.model.User;
import bookproject.library_management.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service указывает, что это бин сервисного слоя
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public User registerUser(User user, PasswordEncoder passwordEncoder) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифруем пароль
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
