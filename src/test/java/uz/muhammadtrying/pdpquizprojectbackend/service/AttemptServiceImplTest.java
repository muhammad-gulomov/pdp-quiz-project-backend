package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.muhammadtrying.pdpquizprojectbackend.dto.AnswerDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.AttemptDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.OptionDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.ResultDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.*;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.*;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttemptRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttemptServiceImplTest {

    private AttemptServiceImpl attemptService;
    private AttemptRepository attemptRepository;
    private QuestionListService questionListService;
    private UserService userService;
    private AnswerService answerService;
    private OptionService optionService;

    @BeforeEach
    void setUp() {
        attemptRepository = Mockito.mock(AttemptRepository.class);
        questionListService = Mockito.mock(QuestionListService.class);
        userService = Mockito.mock(UserService.class);
        answerService = Mockito.mock(AnswerService.class);
        optionService = Mockito.mock(OptionService.class);

        attemptService = new AttemptServiceImpl(
                attemptRepository,
                questionListService,
                userService,
                answerService,
                optionService
        );
    }

    @Test
    void createAnAttemptInvalidInputsTest() {
        List<Answer> answers = Collections.emptyList();
        Integer questionListId = null;

        ResponseEntity<?> response = attemptService.createAnAttempt(answers, questionListId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid inputs", response.getBody());
    }

    @Test
    void createAnAttemptQuestionListNotFoundTest() {
        List<Answer> answers = Arrays.asList(new Answer());
        Integer questionListId = 1;

        when(questionListService.findById(questionListId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = attemptService.createAnAttempt(answers, questionListId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("QuestionList not found", response.getBody());
    }

    @Test
    void createAnAttemptUserNotAuthenticatedTest() {
        List<Answer> answers = Arrays.asList(new Answer());
        Integer questionListId = 1;
        QuestionList questionList = new QuestionList();

        when(questionListService.findById(questionListId)).thenReturn(Optional.of(questionList));
        when(userService.getCurrentUser()).thenReturn(null);

        ResponseEntity<?> response = attemptService.createAnAttempt(answers, questionListId);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not authenticated", response.getBody());
    }

//    @Test
    void createAnAttemptSuccessTest() {
        QuestionList questionList = new QuestionList();
        questionList.setId(1);

        User user = new User();
        Option option = new Option();
        option.setIsCorrect(true);
        Question question = new Question();
        question.setId(1);
        question.setOptions(Collections.singletonList(option));
        option.setQuestion(question);

        Answer answer = new Answer();
        answer.setChosenOption(option);

        List<Answer> answers = Arrays.asList(answer);
        Integer questionListId = 1;

        when(questionListService.findById(questionListId)).thenReturn(Optional.of(questionList));
        when(userService.getCurrentUser()).thenReturn(user);
        when(attemptRepository.save(any(Attempt.class))).thenReturn(new Attempt());

        ResultDTO resultDTO = new ResultDTO();
        when(attemptService.formResultDTO(any(QuestionList.class), anyList())).thenReturn(resultDTO);

        ResponseEntity<?> response = attemptService.createAnAttempt(answers, questionListId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resultDTO, response.getBody());
    }
    @Test
    void findAllByCategoryOrderByScoreDescTest() {
        Category category = new Category();

        User user1 = new User();
        User user2 = new User();

        Attempt attempt1 = new Attempt();
        Attempt attempt2 = new Attempt();

        Option correctOption = new Option();
        correctOption.setIsCorrect(true); // Assume correct option for scoring
        Answer answer1 = new Answer();
        answer1.setChosenOption(correctOption);
        Answer answer2 = new Answer();
        answer2.setChosenOption(correctOption);

        attempt1.setAnswers(Collections.singletonList(answer1));
        attempt2.setAnswers(Collections.singletonList(answer2));

        Mockito.when(attemptRepository.findByUserAndCategory(user1, category)).thenReturn(Collections.singletonList(attempt1));
        Mockito.when(attemptRepository.findByUserAndCategory(user2, category)).thenReturn(Collections.singletonList(attempt2));
        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> sortedUsers = attemptService.findAllByCategoryOrderByScoreDesc(category);

        assertNotNull(sortedUsers);
        assertTrue(sortedUsers.size() >= 2);

    }
    @Test
    void calculateTotalScoreByUserAndCategoryTest() {
        User user = new User();
        Category category = new Category();

        Option correctOption = new Option();
        correctOption.setIsCorrect(true);

        Option incorrectOption = new Option();
        incorrectOption.setIsCorrect(false);

        Answer answer1 = new Answer();
        answer1.setChosenOption(correctOption);

        Answer answer2 = new Answer();
        answer2.setChosenOption(incorrectOption);

        Attempt attempt = new Attempt();
        attempt.setAnswers(Arrays.asList(answer1, answer2));

        when(attemptRepository.findByUserAndCategory(user, category)).thenReturn(Collections.singletonList(attempt));

        int score = attemptService.calculateTotalScoreByUserAndCategory(user, category);

        assertEquals(1, score);
    }

    @Test
    void fromAttemptDTOtoEntityTest() {
        AttemptDTO attemptDTO = new AttemptDTO();
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setChosenOption(new OptionDTO());
        attemptDTO.setAnswers(Collections.singletonList(answerDTO));

        Option option = new Option();
        when(optionService.findById(anyInt())).thenReturn(Optional.of(option));

        List<Answer> answers = attemptService.fromAttemptDTOtoEntity(attemptDTO);

        assertNotNull(answers);
        assertFalse(answers.isEmpty());
    }

    @Test
    void saveAnswersTest() {
        Answer answer = new Answer();
        when(answerService.findByChosenOption(any())).thenReturn(Optional.empty());

        attemptService.saveAnswers(Collections.singletonList(answer));

        verify(answerService).save(answer);
    }

    @Test
    void getOneTest() {
        int id = 1;
        Attempt attempt = new Attempt();
        when(attemptRepository.findById(id)).thenReturn(Optional.of(attempt));

        Optional<Attempt> result = attemptService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(attempt, result.get());
    }

    @Test
    void getOneAttemptNotFoundTest() {
        int id = 1;
        when(attemptRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Attempt> result = attemptService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteTest() {
        Attempt attempt = new Attempt();
        attemptService.delete(attempt);

        verify(attemptRepository).delete(attempt);
    }
}