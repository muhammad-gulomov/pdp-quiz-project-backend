package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.AttemptDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.ResultDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;

import java.util.List;
import java.util.Optional;

@Service
public interface AttemptService {
    void save(Attempt attempt);

    ResponseEntity<?> createAnAttempt(List<Answer> answers, Integer questionListId);

    int calculateTotalScoreByUserAndCategory(User user, Category category);

    List<User> findAllByCategoryOrderByScoreDesc(Category category);

    List<Attempt> findAll();

    Optional<Attempt> findById(int attemptId);

    void delete(Attempt attempt);

    List<Answer> fromAttemptDTOtoEntity(AttemptDTO attemptDTO);
}
