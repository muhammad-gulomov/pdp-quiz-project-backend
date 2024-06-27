package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.pdpquizprojectbackend.entity.TempUser;

public interface TempUserRepository extends JpaRepository<TempUser, Integer> {
    TempUser findByEmail(String email);

    void deleteByEmail(String email);
}