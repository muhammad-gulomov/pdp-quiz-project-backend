package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Test
    void save() {
        Option option = Option.builder().optionContent("Option A").isCorrect(true).build();
        optionRepository.save(option);

        Answer answer = Answer.builder().chosenOption(option).timeSpent(10.0f).score(5).build();
        Answer savedAnswer = answerRepository.save(answer);

        Optional<Answer> answerOpt = answerRepository.findById(savedAnswer.getId());
        assertTrue(answerOpt.isPresent());
    }

    @Test
    void findAnswerByChosenOption() {
        Option option = Option.builder().optionContent("Option A").isCorrect(true).build();
        optionRepository.save(option);

        Answer answer = Answer.builder().chosenOption(option).timeSpent(10.0f).score(5).build();
        answerRepository.save(answer);

        Optional<Answer> answerOpt = answerRepository.findAnswerByChosenOption(option);
        assertTrue(answerOpt.isPresent());
    }
}