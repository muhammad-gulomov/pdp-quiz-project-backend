package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Question;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.ModuleService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.ModuleRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionListRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final QuestionListRepository questionListRepository;
    private final QuestionRepository questionRepository;

    @Override
    public void save(Module module) {
        moduleRepository.save(module);
    }

    @Override
    public ResponseEntity<?> getAllByCategoryAndQLdifficulty(Integer categoryId, String difficulty) {
        Map<String, Object> result = new HashMap<>();

        List<Module> modules = moduleRepository.fetchAllByCategoryId(categoryId);
        List<QuestionList> questionLists = fetchQuestionLists(modules);
        List<Question> questions = fetchQuestions(questionLists);

        result.put("questions", questions);
        result.put("questionLists", questionLists);
        result.put("modules", modules);

        return ResponseEntity.ok().body(result);
    }

    private List<Question> fetchQuestions(List<QuestionList> questionLists) {
        int[] questionListIds = questionLists.stream().mapToInt(QuestionList::getId).toArray();
        return questionRepository.findAllByQuestionListId(questionListIds);
    }

    private List<QuestionList> fetchQuestionLists(List<Module> modules) {
        int[] moduleIds = modules.stream().mapToInt(Module::getId).toArray();
        return questionListRepository.fetchAllByModuleIds(moduleIds);
    }
}
