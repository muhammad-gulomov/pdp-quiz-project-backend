package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionListRepositoryTest {

    @Autowired
    private QuestionListRepository questionListRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    void save() {
        Module module = Module.builder().name("Physics").build();
        moduleRepository.save(module);

        QuestionList questionList = QuestionList.builder().name("Sample Questions").module(module).difficulty(DifficultyEnum.EASY).build();
        QuestionList savedQuestionList = questionListRepository.save(questionList);

        Optional<QuestionList> questionListOpt = questionListRepository.findById(savedQuestionList.getId());
        assertTrue(questionListOpt.isPresent());
    }

    @Test
    void findAllByModuleAndDifficulty() {
        Module module = Module.builder().name("Physics").build();
        moduleRepository.save(module);

        QuestionList questionList = QuestionList.builder().name("Sample Questions").module(module).difficulty(DifficultyEnum.EASY).build();
        questionListRepository.save(questionList);

        List<QuestionList> questionLists = questionListRepository.findAllByModuleAndDifficulty(module, DifficultyEnum.EASY);
        assertFalse(questionLists.isEmpty());
    }

    @Test
    void findAllByModule() {
        Module module = Module.builder().name("Test Module").build();
        moduleRepository.save(module);

        QuestionList questionList1 = QuestionList.builder().name("Test question list 1").module(module).build();
        QuestionList questionList2 = QuestionList.builder().name("Test question list 2").module(module).build();

        questionListRepository.save(questionList1);
        questionListRepository.save(questionList2);

        List<QuestionList> res = questionListRepository.findAllByModule(module);

        assertEquals(res.size(), 2);
    }
}