package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionListRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestionListServiceImplTest {

    private QuestionListServiceImpl questionListService;
    private QuestionListRepository questionListRepository;

    @BeforeEach
    public void setUp() {
        this.questionListRepository = Mockito.mock(QuestionListRepository.class);
        this.questionListService = new QuestionListServiceImpl(questionListRepository);
    }

    @Test
    void save() {
        QuestionList questionList = QuestionList.builder().name("Test name").build();

        Mockito.when(questionListRepository.save(Mockito.any())).thenReturn(QuestionList.builder().name("Test name").build());

        QuestionList savedQuestionList = questionListService.save(questionList);

        assertEquals(savedQuestionList.getName(), questionList.getName());
    }

    @Test
    void findById() {
        QuestionList questionList = QuestionList.builder().name("Test name").build();

        Mockito.when(questionListRepository.findById(Mockito.any())).thenReturn(Optional.of(questionList));

        Optional<QuestionList> qlOpt = questionListService.findById(1);

        assertTrue(qlOpt.isPresent());
        assertEquals(qlOpt.get().getName(), questionList.getName());
    }

    @Test
    void findByIdNotFound() {
        Mockito.when(questionListRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Optional<QuestionList> qlOpt = questionListService.findById(1);

        assertFalse(qlOpt.isPresent());
    }
}