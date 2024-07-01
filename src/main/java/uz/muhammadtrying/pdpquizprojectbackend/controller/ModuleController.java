package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.ModuleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/module")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;

    @GetMapping
    public ResponseEntity<List<Module>> getAllModules() {
        List<Module> modules = moduleService.findAll();
        return ResponseEntity.ok(modules);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteById(@RequestParam int moduleId) {
        Optional<Module> moduleOptional = moduleService.findById(moduleId);
        if (moduleOptional.isPresent()) {
            Module module = moduleOptional.get();
            moduleService.delete(module);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getall/category/difficulty")
    public ResponseEntity<?> getAllByCategoryAndDifficulty(@RequestParam Integer categoryId, @RequestParam String difficulty) {
        return ResponseEntity.ok().body(moduleService.getAllByCategoryAndQLdifficulty(categoryId, difficulty));
    }
}