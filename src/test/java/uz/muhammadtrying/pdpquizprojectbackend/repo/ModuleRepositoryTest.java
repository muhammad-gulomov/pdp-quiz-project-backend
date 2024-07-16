package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ModuleRepositoryTest {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void save() {
        Category category = Category.builder().name("Science").build();
        categoryRepository.save(category);

        Module module = Module.builder().name("Physics").category(category).build();
        Module savedModule = moduleRepository.save(module);

        Optional<Module> moduleOpt = moduleRepository.findById(savedModule.getId());
        assertTrue(moduleOpt.isPresent());
    }

    @Test
    void fetchAllByCategoryId() {
        Category category = Category.builder().name("Science").build();
        categoryRepository.save(category);

        Module module = Module.builder().name("Physics").category(category).build();
        moduleRepository.save(module);

        List<Module> moduleList = moduleRepository.fetchAllByCategoryId(category.getId());
        assertTrue(moduleList.size() > 0);
    }

    @Test
    void findAllByCategory() {
        Category category = Category.builder().name("Science").build();
        categoryRepository.save(category);

        Module module = Module.builder().name("Physics").category(category).build();
        moduleRepository.save(module);

        List<Module> moduleList = moduleRepository.findAllByCategory(category);
        assertTrue(moduleList.size() > 0);
    }
}