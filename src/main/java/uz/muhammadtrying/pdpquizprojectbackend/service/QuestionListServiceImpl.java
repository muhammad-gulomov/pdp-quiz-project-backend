package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.QuestionListService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionListRepository;

@Service
@RequiredArgsConstructor
@Service
public class QuestionListServiceImpl implements QuestionListService {

    private final QuestionListRepository questionListRepository;

    @Override
    public void save(QuestionList questionList) {
        questionListRepository.save(questionList);
    }
}
