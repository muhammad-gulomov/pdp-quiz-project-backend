package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;

import java.util.List;
import java.util.Optional;

@Service
public interface ModuleService {
    void save(Module module);

    ResponseEntity<?> getAllByCategoryAndQLdifficulty(Integer categoryId, String difficulty);

    List<Module> findAll();

    Optional<Module> findById(int moduleId);

    void delete(Module module);
}
