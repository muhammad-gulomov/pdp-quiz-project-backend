package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryStatDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.CategoryService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModuleRepository moduleRepository;
    private final AttemptRepository attemptRepository;
    private final QuestionListRepository questionListRepository;

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAllDTO();
    }

    @Override
    public List<CategoryStatDTO> fetchCategoryStats() {
        return categoryRepository.findAll().stream().map(category -> {
            List<Module> modules = moduleRepository.findAllByCategory(category);

            int modulesFinished = (int) modules.stream()
                    .filter(module -> attemptRepository.countByQuestionListModule(module) > 0)
                    .count();

            int easyQuestionListCount = (int) modules.stream()
                    .flatMap(module -> questionListRepository.findAllByModuleAndDifficulty(module, DifficultyEnum.EASY).stream())
                    .count();

            int mediumQuestionListCount = (int) modules.stream()
                    .flatMap(module -> questionListRepository.findAllByModuleAndDifficulty(module, DifficultyEnum.MEDIUM).stream())
                    .count();

            int hardQuestionListCount = (int) modules.stream()
                    .flatMap(module -> questionListRepository.findAllByModuleAndDifficulty(module, DifficultyEnum.HARD).stream())
                    .count();

            return new CategoryStatDTO(
                    category.getId(),
                    category.getName(),
                    category.getPhoto(),
                    modulesFinished,
                    easyQuestionListCount,
                    mediumQuestionListCount,
                    hardQuestionListCount
            );
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }
}