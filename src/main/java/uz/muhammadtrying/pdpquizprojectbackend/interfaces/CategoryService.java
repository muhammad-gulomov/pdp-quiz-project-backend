package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;

@Service
public interface CategoryService {
    void save(Category category);
}
