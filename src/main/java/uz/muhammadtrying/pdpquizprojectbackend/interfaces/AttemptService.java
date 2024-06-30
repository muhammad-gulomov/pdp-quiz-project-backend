package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;

import java.util.List;

@Service
public interface AttemptService {
    void save(Attempt attempt);

    ResponseEntity<?> createAnAttempt(List<Answer> answers, Integer questionListId);

    int calculateTotalScoreByUserAndCategory(User user, Category category);

    List<User> findAllByCategoryOrderByScoreDesc(Category category);
}
