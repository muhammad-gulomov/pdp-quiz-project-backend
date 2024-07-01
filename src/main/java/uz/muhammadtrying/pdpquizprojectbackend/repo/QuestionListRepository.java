package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;

import java.util.List;

public interface QuestionListRepository extends JpaRepository<QuestionList, Integer> {
    List<QuestionList> findAllByModuleAndDifficulty(Module module, DifficultyEnum difficulty);

    List<QuestionList> findAllByModule(Module module);
}