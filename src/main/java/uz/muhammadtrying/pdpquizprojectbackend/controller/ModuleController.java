package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.ModuleService;

@RestController
@RequestMapping("/api/module")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;

    @GetMapping("/getall/category/difficulty")
    public ResponseEntity<?> getAllByCategoryAndDifficulty(@RequestParam Integer categoryId, @RequestParam String difficulty) {
        return ResponseEntity.ok().body(moduleService.getAllByCategoryAndQLdifficulty(categoryId, difficulty));
    }
}