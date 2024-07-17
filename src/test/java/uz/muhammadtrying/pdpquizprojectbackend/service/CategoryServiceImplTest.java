package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryStatDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.*;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttemptRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.CategoryRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.ModuleRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionListRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    private CategoryServiceImpl categoryService;
    private CategoryRepository categoryRepository;
    private ModuleRepository moduleRepository;
    private AttemptRepository attemptRepository;
    private QuestionListRepository questionListRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        moduleRepository = mock(ModuleRepository.class);
        attemptRepository = mock(AttemptRepository.class);
        questionListRepository = mock(QuestionListRepository.class);
        userService = mock(UserService.class);

        categoryService = new CategoryServiceImpl(categoryRepository, moduleRepository, attemptRepository, questionListRepository, userService);


    }

    @Test
    void fetchCategoryStats_ShouldReturnCategoryStats() {
        Integer userId = 1;
        Category category = new Category();
        category.setId(1);
        category.setName("Category 1");
        category.setAttachment(new Attachment());
        category.getAttachment().setId(1);

        Module module = new Module();
        module.setId(1);
        module.setQuestionLists(Arrays.asList(new QuestionList(), new QuestionList()));

        User user = new User();
        user.setId(userId);

        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(category));
        Mockito.when(moduleRepository.findAllByCategory(category)).thenReturn(List.of(module));
        Mockito.when(questionListRepository.findAllByModuleAndDifficulty(Mockito.any(), eq(DifficultyEnum.EASY)))
                .thenReturn(Arrays.asList(new QuestionList(), new QuestionList()));
        Mockito.when(questionListRepository.findAllByModuleAndDifficulty(Mockito.any(), eq(DifficultyEnum.MEDIUM)))
                .thenReturn(Arrays.asList(new QuestionList()));
        Mockito.when(questionListRepository.findAllByModuleAndDifficulty(Mockito.any(), eq(DifficultyEnum.HARD)))
                .thenReturn(Arrays.asList(new QuestionList(), new QuestionList(), new QuestionList()));
        Mockito.when(attemptRepository.isQuestionListSolvedByUser(Mockito.any(), eq(userId))).thenReturn(true);

        List<CategoryStatDTO> result = categoryService.fetchCategoryStats();

        System.out.println("Result: " + result);

        assertNotNull(result, "The result should not be null.");
        assertEquals(1, result.size(), "There should be exactly one CategoryStatDTO in the result.");

        CategoryStatDTO categoryStatDTO = result.get(0);
        assertEquals(category.getId(), categoryStatDTO.getId(), "Category ID should match.");
    }

    @Test
    void findById_ShouldReturnCategory() {
        Integer categoryId = 1;
        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(categoryId, result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyOptionalWhenCategoryNotFound() {
        Integer categoryId = 1;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.findById(categoryId);

        assertFalse(result.isPresent());
    }

    @Test
    void findAllDTO_ShouldReturnCategoryDTOList() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);
        categoryDTO.setName("Category DTO");

        when(categoryRepository.findAllDTO()).thenReturn(Collections.singletonList(categoryDTO));

        List<CategoryDTO> result = categoryService.findAllDTO();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Category DTO", result.get(0).getName());
    }

    @Test
    void save_ShouldSaveCategory() {
        Category category = new Category();
        category.setId(1);

        categoryService.save(category);

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void findAllTest() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.findAll();

        assertEquals(categories.size(), result.size());
        assertEquals(categories.get(0), result.get(0)); // Check if the first category matches
    }

    @Test
    void deleteCategoryNotFoundTest() {
        Category category = new Category();
        Mockito.doThrow(new RuntimeException("Category not found")).when(categoryRepository).delete(category);

        assertThrows(RuntimeException.class, () -> {
            categoryService.delete(category);
        });
    }

    @Test
    void deleteValidCategoryTest() {
        Category category = new Category();
        Mockito.doNothing().when(categoryRepository).delete(category);

        assertDoesNotThrow(() -> {
            categoryService.delete(category);
        });
    }


}