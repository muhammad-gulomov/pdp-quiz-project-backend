package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.ModuleService;
import uz.muhammadtrying.pdpquizprojectbackend.projections.ModuleProjection;

import java.util.List;

@RestController
@RequestMapping("/api/module")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;

    @GetMapping("/chosen/category/modules")
    public ResponseEntity<List<ModuleProjection>> getModulesOfChosenCategory(
            @RequestParam String difficulty,
            @RequestParam Integer chosenCategoryId
    ) {
        return ResponseEntity.ok( moduleService.findAllByCategoryId(chosenCategoryId, difficulty));
    }
}
