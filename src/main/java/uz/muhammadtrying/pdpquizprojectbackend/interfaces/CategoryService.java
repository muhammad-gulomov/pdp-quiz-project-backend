package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryStatDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;

import java.util.List;

@Service
public interface CategoryService {
    void save(Category category);

    List<CategoryDTO> findAll();

    List<CategoryStatDTO> fetchCategoryStats();
}
