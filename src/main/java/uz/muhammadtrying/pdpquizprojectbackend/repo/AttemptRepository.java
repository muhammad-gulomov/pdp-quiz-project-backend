package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attempt;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;

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

    @Query("""
            SELECT CASE WHEN COUNT(a) = COUNT(q) THEN TRUE ELSE FALSE END
            FROM Question q
            LEFT JOIN q.options o ON o.isCorrect = TRUE
            LEFT JOIN Answer ans ON ans.chosenOption = o
            LEFT JOIN Attempt a ON a.questionList = q.questionList AND a.user.id = :userId
            WHERE q.questionList = :questionList
            GROUP BY q.questionList
            """)
    boolean isQuestionListSolvedByUser(@Param("questionList") QuestionList questionList, @Param("userId") Integer userId);

    @Query("SELECT a FROM Attempt a WHERE a.user = :user AND a.questionList.module.category = :category")
    List<Attempt> findByUserAndCategory(@Param("user") User user, @Param("category") Category category);
}
