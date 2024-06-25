package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;

public interface QuestionListRepository extends JpaRepository<QuestionList, Integer> {
}