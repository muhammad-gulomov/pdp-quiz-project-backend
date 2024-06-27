package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Question;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.QuestionService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionRepository;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }
}
