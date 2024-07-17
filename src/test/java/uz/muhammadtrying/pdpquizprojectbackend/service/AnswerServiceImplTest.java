package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AnswerRepository;

import java.util.Optional;


class AnswerServiceImplTest {

    private AnswerServiceImpl answerService;

    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        this.answerRepository = Mockito.mock();
        this.answerService = new AnswerServiceImpl(answerRepository);
    }

    @Test
    void findByChosenOption() {
        Option option = Option.builder().optionContent("Option A").isCorrect(true).build();
        Answer answer = Answer.builder().chosenOption(option).build();

        Mockito.when(answerRepository.findAnswerByChosenOption(option)).thenReturn(Optional.of(answer));

        Optional<Answer> result = answerService.findByChosenOption(option);
        assertTrue(result.isPresent());
        assertEquals(option, result.get().getChosenOption());
    }

    @Test
    void save() {
        Answer answer = Answer.builder().build();

        Mockito.when(answerRepository.save(answer)).thenReturn(answer);

        Answer savedAnswer = answerService.save(answer);
        assertNotNull(savedAnswer);
        assertEquals(answer, savedAnswer);
    }
}