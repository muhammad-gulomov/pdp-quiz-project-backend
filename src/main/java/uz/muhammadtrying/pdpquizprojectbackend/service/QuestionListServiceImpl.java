package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.QuestionListService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionListRepository;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class QuestionListServiceImpl implements QuestionListService {

    private final QuestionListRepository questionListRepository;

    @Override
    public QuestionList save(QuestionList questionList) {
        return questionListRepository.save(questionList);
    }

    @Override
    public Optional<QuestionList> findById(Integer questionListId) {
        return questionListRepository.findById(questionListId);
    }
}
