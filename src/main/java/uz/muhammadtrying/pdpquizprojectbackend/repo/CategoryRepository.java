package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT new uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO(c.id,c.name, c.attachment.id) FROM Category c")
    List<CategoryDTO> findAllDTO();
}