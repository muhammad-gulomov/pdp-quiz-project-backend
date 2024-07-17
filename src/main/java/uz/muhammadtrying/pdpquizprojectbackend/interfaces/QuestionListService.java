package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;

import java.util.Optional;

@Service
public interface QuestionListService {
    QuestionList save(QuestionList questionList);

    Optional<QuestionList> findById(Integer questionListId);
}
