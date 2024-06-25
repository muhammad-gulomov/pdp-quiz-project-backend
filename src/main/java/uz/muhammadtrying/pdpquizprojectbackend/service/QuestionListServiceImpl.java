package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.QuestionListService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.QuestionListRepository;

@RequiredArgsConstructor
public class QuestionListServiceImpl implements QuestionListService {

    private final QuestionListRepository questionListRepository;

    @Override
    public void save(QuestionList questionList) {
        questionListRepository.save(questionList);
    }
}
