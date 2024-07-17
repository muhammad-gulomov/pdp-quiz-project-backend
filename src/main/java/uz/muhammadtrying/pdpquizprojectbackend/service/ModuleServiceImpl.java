package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.ModuleService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttemptRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.ModuleRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final AttemptRepository attemptRepository;

    @Override
    public List<Module> findAll() {
        return moduleRepository.findAll();
    }

    @Override
    public Optional<Module> findById(int moduleId) {
        return moduleRepository.findById(moduleId);
    }

    @Override
    public void delete(Module module) {
        moduleRepository.delete(module);
    }

    @Override
    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public ResponseEntity<?> getAllByCategoryAndQLdifficulty(Integer categoryId, String difficulty) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        List<Module> modules = moduleRepository.fetchAllByCategoryId(categoryId);

        modules.forEach(module -> {
            List<QuestionList> filteredQuestionLists = module.getQuestionLists().stream()
                    .filter(questionList -> questionList.getDifficulty().equals(DifficultyEnum.valueOf(difficulty)))
                    .collect(Collectors.toList());
            module.setQuestionLists(filteredQuestionLists);
        });

        List<Attempt> attempts = fetchAttempts(modules);

        result.put("modules", modules);
        result.put("attempts", attempts);

        return ResponseEntity.ok().body(result);
    }

    List<Attempt> fetchAttempts(List<Module> modules) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Integer> questionListIds = modules.stream()
                .flatMap(module -> module.getQuestionLists().stream())
                .map(QuestionList::getId)
                .collect(Collectors.toList());
        return attemptRepository.fetchLatestAttemptsByQuestionListIdsAndEmail(questionListIds, email);
    }
}
