package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.ModuleService;
import uz.muhammadtrying.pdpquizprojectbackend.projections.ModuleProjection;
import uz.muhammadtrying.pdpquizprojectbackend.repo.ModuleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public void save(Module module) {
        moduleRepository.save(module);
    }

    @Override
    public List<ModuleProjection> findAllByCategoryId(Integer chosenCategoryId, String difficulty) {
        return moduleRepository.findAllByCategoryAndDifficulty(chosenCategoryId, difficulty);
    }
}
