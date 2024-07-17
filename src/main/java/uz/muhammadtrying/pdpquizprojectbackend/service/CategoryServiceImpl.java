package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryStatDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.CategoryService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttemptRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.CategoryRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.ModuleRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionListRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModuleRepository moduleRepository;
    private final AttemptRepository attemptRepository;
    private final QuestionListRepository questionListRepository;
    private final UserService userService;

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }


    @Override
    public List<CategoryStatDTO> fetchCategoryStats() {

        Integer userId = userService.getCurrentUser().getId();

        return categoryRepository.findAll().stream().map(category -> {
            List<Module> modules = moduleRepository.findAllByCategory(category);

            int modulesFinished = (int) modules.stream()
                    .filter(module -> isModuleSolvedByUser(module, userId))
                    .count();

            Map<String, Integer> easyQuestionList = new HashMap<>();
            Map<String, Integer> mediumQuestionList = new HashMap<>();
            Map<String, Integer> hardQuestionList = new HashMap<>();

            updateQuestionListCounts(modules, easyQuestionList, DifficultyEnum.EASY, userId);
            updateQuestionListCounts(modules, mediumQuestionList, DifficultyEnum.MEDIUM, userId);
            updateQuestionListCounts(modules, hardQuestionList, DifficultyEnum.HARD, userId);

            return new CategoryStatDTO(
                    category.getId(),
                    category.getName(),
                    category.getAttachment().getId(),
                    modulesFinished,
                    easyQuestionList,
                    mediumQuestionList,
                    hardQuestionList
            );
        }).collect(Collectors.toList());
    }

    private void updateQuestionListCounts(List<Module> modules, Map<String, Integer> questionListMap, DifficultyEnum difficulty, Integer userId) {
        questionListMap.put("total", 0);
        questionListMap.put("solved", 0);

        modules.stream()
                .flatMap(module -> questionListRepository.findAllByModuleAndDifficulty(module, difficulty).stream())
                .forEach(questionList -> {
                    questionListMap.put("total", questionListMap.get("total") + 1);
                    if (attemptRepository.isQuestionListSolvedByUser(questionList, userId)) {
                        questionListMap.put("solved", questionListMap.get("solved") + 1);
                    }
                });
    }

    private boolean isModuleSolvedByUser(Module module, Integer userId) {
        return questionListRepository.findAllByModule(module).stream()
                .allMatch(questionList -> attemptRepository.isQuestionListSolvedByUser(questionList, userId));
    }

    @Override
    public Optional<Category> findById(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }


    @Override
    public List<CategoryDTO> findAllDTO() {
        return categoryRepository.findAllDTO();
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
