package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.AnswerService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.AttemptService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.QuestionListService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttemptRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> createAnAttempt(List<Answer> answers, Integer questionListId) {

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

        return ResponseEntity.status(HttpStatus.CREATED).body("Attempt created successfully");
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
