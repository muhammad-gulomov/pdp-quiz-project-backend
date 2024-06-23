package uz.muhammadtrying.pdpquizprojectbackend.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;
import uz.muhammadtrying.pdpquizprojectbackend.repo.UserRepository;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder()
                .firstName("Muhammad")
                .lastName("G'ulomov")
                .email("1")
                .password(passwordEncoder.encode("1"))
                .build();
        userRepository.save(user);
    }
}
