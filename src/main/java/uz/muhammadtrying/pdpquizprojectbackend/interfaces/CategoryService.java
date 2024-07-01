package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryStatDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    void save(Category category);

    List<Category> findAll();

    List<CategoryStatDTO> fetchCategoryStats();

    Optional<Category> findById(Integer categoryId);

    void delete(Category category);

    List<CategoryDTO> findAllDTO();
}
