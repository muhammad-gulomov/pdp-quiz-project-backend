package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.AttemptService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attempt")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptService attemptService;

    @PostMapping("/create")
    public ResponseEntity<?> createAnAttempt(@RequestBody List<Answer> answers, @RequestParam Integer questionListId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attemptService.createAnAttempt(answers, questionListId));
    }
}
