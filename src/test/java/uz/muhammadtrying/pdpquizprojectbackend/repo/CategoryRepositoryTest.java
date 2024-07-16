package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void save() {
        Category category = Category.builder().name("Science").build();
        Category savedCategory = categoryRepository.save(category);

        Optional<Category> categoryOpt = categoryRepository.findById(savedCategory.getId());
        assertTrue(categoryOpt.isPresent());
    }

    @Test
    void findAllDTO() {
        Category category = Category.builder().name("Science").build();
        categoryRepository.save(category);

        List<CategoryDTO> categoryDTOList = categoryRepository.findAllDTO();
        assertTrue(categoryDTOList.size() > 0);
    }
}