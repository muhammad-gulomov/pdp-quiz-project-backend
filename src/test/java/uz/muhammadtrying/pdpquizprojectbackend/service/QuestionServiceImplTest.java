package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Question;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionRepository;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceImplTest {

    private QuestionServiceImpl questionService;
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setUp() {
        this.questionRepository = Mockito.mock(QuestionRepository.class);
        this.questionService = new QuestionServiceImpl(questionRepository);
    }

    @Test
    void save() {
        Question question = Question.builder().questionContent("Test Content2").build();

        Mockito.when(questionRepository.save(question)).thenReturn(question);

        Question savedQuestion = questionService.save(question);

        assertNotNull(savedQuestion);
    }

}