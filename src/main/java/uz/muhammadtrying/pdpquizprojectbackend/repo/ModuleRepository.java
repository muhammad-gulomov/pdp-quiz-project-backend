package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    @Query(value = "from Module m where m.category.id =:categoryId")
    List<Module> fetchAllByCategoryId(Integer categoryId);

    List<Module> findAllByCategory(Category category);
}