package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Question;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void save() {
        Question question = Question.builder()
                .questionContent("What is Java?")
                .seconds(30)
                .build();

        Question savedQuestion = questionRepository.save(question);

        Optional<Question> questionOpt = questionRepository.findById(savedQuestion.getId());
        assertTrue(questionOpt.isPresent());
    }
}