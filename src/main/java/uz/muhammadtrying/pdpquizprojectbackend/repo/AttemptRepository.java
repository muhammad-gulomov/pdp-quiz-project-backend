package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Integer> {
    @Query("""
            SELECT a FROM Attempt a
            WHERE a.id IN (
                SELECT MAX(a2.id) FROM Attempt a2
                WHERE a2.questionList.id IN :questionListIds
                GROUP BY a2.questionList.id
            )
            AND a.user.email = :email
            """)
    List<Attempt> fetchLatestAttemptsByQuestionListIdsAndEmail(
            @Param("questionListIds") List<Integer> questionListIds,
            @Param("email") String email);



    @Query("select a from Attempt a where a.user = :user and a.questionList.module.category = :category")
    List<Attempt> findByUserAndCategory(@Param("user") User user, @Param("category") Category category);
}