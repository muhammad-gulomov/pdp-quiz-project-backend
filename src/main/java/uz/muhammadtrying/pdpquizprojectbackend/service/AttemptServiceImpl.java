package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.ResultDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.*;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.AnswerService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.AttemptService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.QuestionListService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttemptRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttemptServiceImpl implements AttemptService {

    private final AttemptRepository attemptRepository;
    private final QuestionListService questionListService;
    private final UserService userService;
    private final AnswerService answerService;

    @Override
    public void save(Attempt attempt) {
        attemptRepository.save(attempt);
    }

    @Override
    public ResponseEntity<?> createAnAttempt(List<Answer> answers, Integer questionListId) {

        if (answers == null || answers.isEmpty() || questionListId == null) {
            return ResponseEntity.badRequest().body("Invalid inputs");
        }

        Optional<QuestionList> questionListOptional = questionListService.findById(questionListId);

        if (questionListOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("QuestionList not found");
        }
        QuestionList questionList = questionListOptional.get();

        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        saveAnswers(answers);

        createAnAttemptWithValidData(answers, currentUser, questionList);

        ResultDTO resultDTO = formResultDTO(questionList, answers);

        return ResponseEntity.status(HttpStatus.CREATED).body(resultDTO);
    }

    @Override
    public int calculateTotalScoreByUserAndCategory(User user, Category category) {
        List<Attempt> attempts = attemptRepository.findByUserAndCategory(user, category);

        return attempts.stream()
                .flatMap(attempt -> attempt.getAnswers().stream())
                .mapToInt(answer -> answer.getChosenOption().getIsCorrect() ? 1 : 0)
                .sum();
    }

    public List<User> findAllByCategoryOrderByScoreDesc(Category category) {
        List<User> users = userService.findAll();
        Map<User, Integer> userScores = new HashMap<>();

        for (User user : users) {
            int totalScore = calculateTotalScoreByUserAndCategory(user, category);
            userScores.put(user, totalScore);
        }

        return userScores.entrySet().stream()
                .sorted(Map.Entry.<User, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    private ResultDTO formResultDTO(QuestionList questionList, List<Answer> answers) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setQuestionListId(questionList.getId());

        int correctAnswers = 0;
        int incorrectAnswers = 0;

        for (Answer answer : answers) {
            Option chosenOption = answer.getChosenOption();
            Question question = chosenOption.getQuestion();

            Option correctOption = question.getOptions().stream()
                    .filter(Option::getIsCorrect)
                    .findFirst()
                    .orElse(null);

            if (Objects.equals(chosenOption, correctOption)) {
                correctAnswers++;
            } else {
                incorrectAnswers++;
            }
        }

        resultDTO.setCorrectAnswers(correctAnswers);
        resultDTO.setIncorrectAnswers(incorrectAnswers);

        return resultDTO;
    }

    private void createAnAttemptWithValidData(List<Answer> answers, User currentUser, QuestionList questionList) {
        Attempt attempt = Attempt.builder()
                .answers(answers)
                .user(currentUser)
                .dateTime(LocalDateTime.now())
                .questionList(questionList)
                .build();
        attemptRepository.save(attempt);
    }

    private void saveAnswers(List<Answer> answers) {
        for (Answer answer : answers) {
            Optional<Answer> existingAnswerOptional = answerService.findByChosenOption(answer.getChosenOption());
            if (existingAnswerOptional.isPresent()) {
                Answer existingAnswer = existingAnswerOptional.get();
                existingAnswer.setScore(answer.getScore());
                existingAnswer.setTimeSpent(answer.getTimeSpent());
                answerService.save(existingAnswer);
            } else {
                answerService.save(answer);
            }
        }
    }
}
