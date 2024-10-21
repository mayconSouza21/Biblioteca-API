package br.ufpb.Biblioteca_API.Config;

import br.ufpb.Biblioteca_API.Models.User;
import br.ufpb.Biblioteca_API.Repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserSeeder {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seedUsers() {
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));

            admin.setName("Admin User");
            admin.setEmail("admin@example.com");
            admin.setAge(30);
            admin.setCpf("12345678901");
            admin.setAddress("Center");
            admin.setCity("Admin City");
            userRepository.save(admin);
        }
    }
}
