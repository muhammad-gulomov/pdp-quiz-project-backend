package uz.muhammadtrying.pdpquizprojectbackend.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.*;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.CategoryService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.ModuleService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.QuestionListService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.UserRepository;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final ModuleService moduleService;
    private final QuestionListService questionListService;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final QuestionRepository questionRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;


    @Override
    public void run(String... args) {
        if (ddlAuto.equals("create")) {
            generateData();
        }
    }

    private void generateData() {
        generateUser();

        Category category1 = Category.builder().name("Java").build();
        Category category2 = Category.builder().name("Python").build();

        categoryService.save(category1);
        categoryService.save(category2);

        Module module1 = Module.builder().category(category1).name("Module-1").build();
        Module module2 = Module.builder().category(category1).name("Module-2").build();
        Module module3 = Module.builder().category(category2).name("Module-1").build();
        Module module4 = Module.builder().category(category2).name("Module-2").build();

        moduleService.save(module1);
        moduleService.save(module2);
        moduleService.save(module3);
        moduleService.save(module4);

        QuestionList questionList1 = QuestionList.builder().module(module2).difficulty(DifficultyEnum.MEDIUM).name("OOP").build();
        QuestionList questionList2 = QuestionList.builder().module(module2).difficulty(DifficultyEnum.HARD).name("Data Structure").build();
        QuestionList questionList3 = QuestionList.builder().module(module3).difficulty(DifficultyEnum.MEDIUM).name("History of Python").build();
        QuestionList questionList4 = QuestionList.builder().module(module4).difficulty(DifficultyEnum.MEDIUM).name("Data Types").build();
        QuestionList questionList5 = QuestionList.builder().module(module1).difficulty(DifficultyEnum.MEDIUM).name("History of Java").build();

        questionListService.save(questionList1);
        questionListService.save(questionList2);
        questionListService.save(questionList3);
        questionListService.save(questionList4);
        questionListService.save(questionList5);

        Question question1 = Question.builder().questionContent("asas").seconds(new Timestamp(12)).questionList(questionList1).build();
        Question question2 = Question.builder().questionContent("adad").seconds(new Timestamp(12)).questionList(questionList2).build();
        Question question3 = Question.builder().questionContent("asas").seconds(new Timestamp(12)).questionList(questionList3).build();
        Question question4 = Question.builder().questionContent("adassaad").seconds(new Timestamp(12)).questionList(questionList4).build();
        Question question5 = Question.builder().questionContent("adassaad").seconds(new Timestamp(12)).questionList(questionList5).build();

        Question question6 = Question.builder().questionContent("asas").seconds(new Timestamp(12)).questionList(questionList1).build();
        Question question7 = Question.builder().questionContent("adad").seconds(new Timestamp(12)).questionList(questionList2).build();
        Question question8 = Question.builder().questionContent("asas").seconds(new Timestamp(12)).questionList(questionList3).build();
        Question question9 = Question.builder().questionContent("adassaad").seconds(new Timestamp(12)).questionList(questionList4).build();
        Question question10 = Question.builder().questionContent("adassaad").seconds(new Timestamp(12)).questionList(questionList5).build();

        questionRepository.save(question1);
        questionRepository.save(question1);
        questionRepository.save(question1);
        questionRepository.save(question1);
        questionRepository.save(question1);
        questionRepository.save(question1);
        questionRepository.save(question1);
        questionRepository.save(question1);
        questionRepository.save(question1);
    }


    private void generateUser() {
        User user = User.builder()
                .firstName("Muhammad")
                .lastName("G'ulomov")
                .email("muhammadtrying@gmail.com")
                .password(passwordEncoder.encode("1"))
                .build();
        userRepository.save(user);
    }
}
