package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.muhammadtrying.pdpquizprojectbackend.dto.AttemptDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.AttemptService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attempt")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptService attemptService;

    @GetMapping
    public ResponseEntity<List<Attempt>> getAttempts() {
        List<Attempt> attempts = attemptService.findAll();
        return ResponseEntity.ok(attempts);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAttempt(@RequestParam int attemptId) {
        Optional<Attempt> attemptOptional = attemptService.findById(attemptId);
        if (attemptOptional.isPresent()) {
            Attempt attempt = attemptOptional.get();
            attemptService.delete(attempt);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createAnAttempt(@RequestBody AttemptDTO attemptDTO) {
        List<Answer> answers = attemptService.fromAttemptDTOtoEntity(attemptDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(attemptService.createAnAttempt(answers, attemptDTO.getQuestionListId()));
    }
}