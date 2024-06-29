package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;

import java.util.List;

public interface QuestionListRepository extends JpaRepository<QuestionList, Integer> {
    @Query(value = "from QuestionList ql where ql.module.id in :moduleIds")
    List<QuestionList> fetchAllByModuleIds(int[] moduleIds);
}