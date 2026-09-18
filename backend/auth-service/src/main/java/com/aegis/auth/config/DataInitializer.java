package com.aegis.auth.config;

import com.aegis.auth.entity.User;
import com.aegis.auth.enums.Role;
import com.aegis.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Pre-seed Demo Customer if not already present
        if (!userRepository.existsByLoginId("123")) {
            User customer = User.builder()
                    .loginId("123")
                    .name("Sarah Jenkins")
                    .email("sarah.jenkins@aegis-client.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(Role.CUSTOMER)
                    .phone("+1 (555) 234-8901")
                    .enabled(true)
                    .build();
            userRepository.save(customer);
            logger.info("Pre-seeded demo customer: loginId=123, password=password123");
        }

        // Pre-seed Demo Admin / Underwriter if not already present
        if (!userRepository.existsByLoginId("2400030001")) {
            User admin = User.builder()
                    .loginId("2400030001")
                    .name("Marcus Vance")
                    .email("m.vance@aegis-assurance.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .phone("+1 (555) 890-1234")
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            logger.info("Pre-seeded demo admin/officer: loginId=2400030001, password=admin123");
        }
    }
}
