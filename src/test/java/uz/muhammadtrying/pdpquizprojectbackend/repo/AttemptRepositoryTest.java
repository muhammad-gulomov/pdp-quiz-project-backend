package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AttemptRepositoryTest {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionListRepository questionListRepository;

    @Test
    void save() {
        User user = User.builder().email("test@gmail.com").firstName("John").lastName("Doe").password("root123").build();
        userRepository.save(user);

        QuestionList questionList = QuestionList.builder().name("Sample Question List").build();
        questionListRepository.save(questionList);

        Attempt attempt = Attempt.builder().dateTime(LocalDateTime.now()).user(user).questionList(questionList).build();
        Attempt savedAttempt = attemptRepository.save(attempt);

        Optional<Attempt> attemptOpt = attemptRepository.findById(savedAttempt.getId());
        assertTrue(attemptOpt.isPresent());
    }

    @Test
    void fetchLatestAttemptsByQuestionListIdsAndEmail() {
    }

    @Test
    void isQuestionListSolvedByUser() {
        // Add setup code to save required entities and perform the test.
    }

    @Test
    void findByUserAndCategory() {
        // Add setup code to save required entities and perform the test.
    }
}