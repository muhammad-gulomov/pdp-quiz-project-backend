package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User user = User.builder()
                .email("a@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .password("root123")
                .build();

        User savedUser = userRepository.save(user);

        Optional<User> userOpt = userRepository.findById(savedUser.getId());
        assertTrue(userOpt.isPresent());
    }

    @Test
    void findByEmail() {
        User user = User.builder()
                .email("a@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .password("root123")
                .build();
        userRepository.save(user);
        User newUser = userRepository.findByEmail(user.getEmail());
        assertEquals(newUser.getId(), user.getId());
    }
}