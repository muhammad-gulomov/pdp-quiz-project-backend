package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.ModuleDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.projections.ModuleProjection;

import java.util.List;

@Service
public interface ModuleService {
    void save(Module module);

    List<ModuleProjection> findAllByCategoryId(Integer chosenCategoryId, String difficulty);
}
