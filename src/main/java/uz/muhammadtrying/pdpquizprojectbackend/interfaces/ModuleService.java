package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;

@Service
public interface ModuleService {
    void save(Module module);

    ResponseEntity<?> getAllByCategoryAndQLdifficulty(Integer categoryId, String difficulty);
}
