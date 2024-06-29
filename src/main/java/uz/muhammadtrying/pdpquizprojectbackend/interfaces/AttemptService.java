package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;

import java.util.List;

@Service
public interface AttemptService {
    void save(Attempt attempt);

    ResponseEntity<String> createAnAttempt(List<Answer> answers, Integer questionListId);
}
