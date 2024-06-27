package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.CategoryService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAllDTO();
    }
}
