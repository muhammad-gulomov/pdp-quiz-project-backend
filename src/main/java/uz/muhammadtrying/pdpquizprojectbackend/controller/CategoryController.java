package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryStatDTO;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.CategoryService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/get/all")
    public ResponseEntity<?> findAll() {
        Map<String, Object> result = new HashMap<>();
        result.put("categories", categoryService.findAll());
        result.put("user", userService.getCurrentUser());
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/get/stats")
    public ResponseEntity<List<CategoryStatDTO>> getStats() {
        return ResponseEntity.status(200).body(categoryService.fetchCategoryStats());
    }
}
