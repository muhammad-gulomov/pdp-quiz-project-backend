package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
}