package uz.muhammadtrying.pdpquizprojectbackend.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.muhammadtrying.pdpquizprojectbackend.entity.*;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.*;
import uz.muhammadtrying.pdpquizprojectbackend.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CategoryService categoryService;
    private final AnswerService answerService;
    private final AttemptService attemptService;
    private final ModuleService moduleService;
    private final OptionService optionService;
    private final QuestionService questionService;
    private final QuestionListService questionListService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;


    @Override
    public void run(String... args) throws Exception {
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

        Module module1 = Module.builder().category(category1).name("module-1").build();
        Module module2 = Module.builder().category(category1).name("module-2").build();
        Module module3 = Module.builder().category(category2).name("module-1").build();
        Module module4 = Module.builder().category(category2).name("module-2").build();

        moduleService.save(module1);
        moduleService.save(module2);
        moduleService.save(module3);
        moduleService.save(module4);

        QuestionList questionList1 = QuestionList.builder().module(module2).difficulty(DifficultyEnum.MEDIUM).name("OOP").build();
        QuestionList questionList2 = QuestionList.builder().module(module2).difficulty(DifficultyEnum.HARD).name("Data Structure").build();
        QuestionList questionList3 = QuestionList.builder().module(module1).difficulty(DifficultyEnum.EASY).name("Java Basics").build();
        QuestionList questionList4 = QuestionList.builder().module(module3).difficulty(DifficultyEnum.EASY).name("Python Basics").build();
        QuestionList questionList5 = QuestionList.builder().module(module4).difficulty(DifficultyEnum.MEDIUM).name("OOP").build();
        QuestionList questionList6 = QuestionList.builder().module(module4).difficulty(DifficultyEnum.HARD).name("Threading").build();


        questionListService.save(questionList1);
        questionListService.save(questionList2);
        questionListService.save(questionList3);
        questionListService.save(questionList4);
        questionListService.save(questionList5);
        questionListService.save(questionList6);

        Question question1 = Question.builder().questionContent("Constructors are used to:").questionList(questionList1).build();

        Option option1_1 = Option.builder().question(question1).isCorrect(true).optionContent("Initialize a newly created object.").build();
        Option option1_2 = Option.builder().question(question1).isCorrect(true).optionContent("To build a user interface.").build();
        Option option1_3 = Option.builder().question(question1).isCorrect(true).optionContent("To create a sub-class.").build();




    }



    private void generateUser() {
        User user = User.builder()
                .firstName("Muhammad")
                .lastName("G'ulomov")
                .email("muhammadtrying@gmail.com")
                .password(passwordEncoder.encode("1"))
                .build();
        userService.save(user);
    }

}
