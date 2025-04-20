package bookproject.library_management;

import bookproject.library_management.model.User;
import bookproject.library_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LibraryManagementApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (userService.findByEmail("admin@example.com") == null) {
			User admin = new User();
			admin.setName("Admin");
			admin.setEmail("admin@example.com");
			admin.setPassword("admin123");
			userService.registerUser(admin, passwordEncoder);
		}
	}
}
