package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Integer> {
    @Query(value = "from Attempt a where a.id in :questionListIds and a.user.email =:email")
    List<Attempt> fetchAllByQuestionListId(List<Integer> questionListIds, String email);
}