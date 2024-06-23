package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}