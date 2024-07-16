package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.TempUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TempUserRepositoryTest {

    @Autowired
    private TempUserRepository tempUserRepository;

    @Test
    void save() {
        TempUser tempUser = TempUser
                .builder()
                .email("temp@gmail.com")
                .password("password")
                .code("tempcode")
                .firstName("tempFirstname")
                .lastName("tempLastname")
                .build();
        TempUser savedTempUser = tempUserRepository.save(tempUser);

        Optional<TempUser> tempUserOpt = tempUserRepository.findById(savedTempUser.getId());
        assertTrue(tempUserOpt.isPresent());
    }

    @Test
    void findByEmail() {
        TempUser tempUser = TempUser
                .builder()
                .email("temp@gmail.com")
                .password("password")
                .code("tempcode")
                .firstName("tempFirstname")
                .lastName("tempLastname")
                .build();
        tempUserRepository.save(tempUser);

        TempUser foundTempUser = tempUserRepository.findByEmail(tempUser.getEmail());
        assertTrue(foundTempUser != null);
    }
}