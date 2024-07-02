package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.AttemptDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.ResultDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.*;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.*;
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
    private final OptionService optionService;

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

        return ResponseEntity.status(200).body(resultDTO);
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

    @Override
    public int calculateTotalScoreByUserAndCategory(User user, Category category) {
        List<Attempt> attempts = attemptRepository.findByUserAndCategory(user, category);

        return attempts.stream()
                .flatMap(attempt -> attempt.getAnswers().stream())
                .mapToInt(answer -> answer.getChosenOption().getIsCorrect() ? 1 : 0)
                .sum();
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

    @Override
    public List<Answer> fromAttemptDTOtoEntity(AttemptDTO attemptDTO) {
        List<Answer> answers = attemptDTO.getAnswers().stream().map(dto -> {
            Answer answer = new Answer();
            Optional<Option> optionOptional = optionService.findById(dto.getChosenOption().getId());
            if (optionOptional.isPresent()) {
                Option option = optionOptional.get();
                answer.setChosenOption(option);
            }
            answer.setTimeSpent(dto.getTimeSpent());
            return answer;
        }).collect(Collectors.toList());
        return answers;
    }

    @Override
    public List<Attempt> findAll() {
        return attemptRepository.findAll();
    }

    @Override
    public Optional<Attempt> findById(int attemptId) {
        return attemptRepository.findById(attemptId);
    }

    @Override
    public void delete(Attempt attempt) {
        attemptRepository.delete(attempt);
    }
}
