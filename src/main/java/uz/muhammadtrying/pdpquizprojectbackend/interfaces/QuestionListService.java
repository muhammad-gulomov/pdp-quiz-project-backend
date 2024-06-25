package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;

@Service
public interface QuestionListService {
    void save(QuestionList questionList);
}
