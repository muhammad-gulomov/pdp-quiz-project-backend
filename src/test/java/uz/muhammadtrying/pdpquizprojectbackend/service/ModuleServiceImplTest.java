package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttemptRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.ModuleRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ModuleServiceImplTest {

    private ModuleServiceImpl moduleService;
    private ModuleRepository moduleRepository;
    private AttemptRepository attemptRepository;

    @BeforeEach
    void setUp() {
        moduleRepository = mock(ModuleRepository.class);
        attemptRepository = mock(AttemptRepository.class);
        moduleService = new ModuleServiceImpl(moduleRepository, attemptRepository);

        SecurityContextHolder.getContext().setAuthentication(Mockito.mock(org.springframework.security.core.Authentication.class));
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("user@example.com");
    }

    @Test
    void findAll_ShouldReturnAllModules() {
        List<Module> modules = List.of(new Module(), new Module());
        Mockito.when(moduleRepository.findAll()).thenReturn(modules);

        List<Module> result = moduleService.findAll();

        assertEquals(modules, result);
    }

    @Test
    void findById_ShouldReturnModule() {
        int moduleId = 1;
        Module module = new Module();
        Mockito.when(moduleRepository.findById(moduleId)).thenReturn(Optional.of(module));

        Optional<Module> result = moduleService.findById(moduleId);

        assertTrue(result.isPresent());
        assertEquals(module, result.get());
    }

    @Test
    void findById_ShouldReturnEmptyOptionalWhenModuleNotFound() {
        int moduleId = 1;
        Mockito.when(moduleRepository.findById(moduleId)).thenReturn(Optional.empty());

        Optional<Module> result = moduleService.findById(moduleId);

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldSaveModule() {
        Module module = new Module();
        Mockito.when(moduleRepository.save(module)).thenReturn(module);

        Module result = moduleService.save(module);

        assertEquals(module, result);
    }

    @Test
    void delete_ShouldDeleteModule() {
        Module module = new Module();

        moduleService.delete(module);

        Mockito.doNothing().when(moduleRepository).delete(module);
    }

    @Test
    void getAllByCategoryAndQLdifficultyTest() {
        int categoryId = 1;
        String difficulty = "EASY";

        Module module1 = new Module();
        QuestionList ql1 = new QuestionList();
        ql1.setId(1);
        ql1.setDifficulty(DifficultyEnum.EASY);
        QuestionList ql2 = new QuestionList();
        ql2.setId(2);
        ql2.setDifficulty(DifficultyEnum.MEDIUM);
        module1.setQuestionLists(Arrays.asList(ql1, ql2));

        Module module2 = new Module();
        QuestionList ql3 = new QuestionList();
        ql3.setId(3);
        ql3.setDifficulty(DifficultyEnum.EASY);
        QuestionList ql4 = new QuestionList();
        ql4.setId(4);
        ql4.setDifficulty(DifficultyEnum.HARD);
        module2.setQuestionLists(Arrays.asList(ql3, ql4));

        List<Module> modules = Arrays.asList(module1, module2);

        Mockito.when(moduleRepository.fetchAllByCategoryId(categoryId)).thenReturn(modules);

        List<Attempt> attempts = Collections.singletonList(new Attempt());
        Mockito.when(attemptRepository.fetchLatestAttemptsByQuestionListIdsAndEmail(
                Arrays.asList(1, 3), "user@example.com")).thenReturn(attempts);

        ResponseEntity<?> response = moduleService.getAllByCategoryAndQLdifficulty(categoryId, difficulty);

        assertNotNull(response);
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertTrue(body.containsKey("modules"));
        assertTrue(body.containsKey("attempts"));

        List<Module> resultModules = (List<Module>) body.get("modules");
        assertEquals(2, resultModules.size());
        assertTrue(resultModules.stream().allMatch(m -> m.getQuestionLists().stream()
                .allMatch(ql -> ql.getDifficulty() == DifficultyEnum.EASY)));

        List<Attempt> resultAttempts = (List<Attempt>) body.get("attempts");
        assertEquals(1, resultAttempts.size());
    }

    @Test
    void fetchAttemptsTest() {
        // Prepare mock data
        Module module = new Module();
        QuestionList ql1 = new QuestionList();
        ql1.setId(1);
        ql1.setDifficulty(DifficultyEnum.EASY);
        QuestionList ql2 = new QuestionList();
        ql2.setId(2);
        ql2.setDifficulty(DifficultyEnum.MEDIUM);
        module.setQuestionLists(Arrays.asList(ql1, ql2));
        List<Module> modules = Collections.singletonList(module);

        List<Attempt> attempts = Arrays.asList(new Attempt(), new Attempt());
        Mockito.when(attemptRepository.fetchLatestAttemptsByQuestionListIdsAndEmail(
                Arrays.asList(1, 2), "user@example.com")).thenReturn(attempts);

        List<Attempt> resultAttempts = moduleService.fetchAttempts(modules);

        assertNotNull(resultAttempts);
        assertEquals(2, resultAttempts.size());
    }
}