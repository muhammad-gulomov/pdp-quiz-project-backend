package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.LeaderboardDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.AttemptService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.CategoryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final AttemptService attemptService;
    private final CategoryService categoryService;

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<List<LeaderboardDTO>> getLeaderboard(@PathVariable Integer categoryId) {

        Optional<Category> categoryOptional = categoryService.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        Category category = categoryOptional.get();
        List<User> users = attemptService.findAllByCategoryOrderByScoreDesc(category);
        List<LeaderboardDTO> leaderboard = new ArrayList<>();

        int rank = 1;
        for (User user : users) {
            int score = attemptService.calculateTotalScoreByUserAndCategory(user, category);

            LeaderboardDTO dto = new LeaderboardDTO(
                    new CategoryDTO(category.getId(), category.getName(), category.getAttachment().getId()),
                    rank++,
                    user.getPhoto().getId(),
                    user.getFirstName() + " " + user.getLastName(),
                    score
            );

            leaderboard.add(dto);
        }
        return ResponseEntity.ok(leaderboard);
    }
}
