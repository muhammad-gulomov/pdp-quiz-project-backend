package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryStatDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.CategoryService;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCategory(@RequestParam int categoryId) {
        Optional<Category> categoryOptional = categoryService.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            categoryService.delete(category);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
