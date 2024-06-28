package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.projections.ModuleProjection;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    @Query(nativeQuery = true, value = """
               select m.id, m.name,
                   array_agg(ql) as questionlists,
                   array_agg(q) as questions
            from module m
            join question_list ql
            on m.id = ql.module_id
            join question q
            on ql.id = q.question_list_id
            where m.id = :chosenCategoryId and ql.difficulty = :difficulty
            group by m.id
            """)
    List<ModuleProjection> findAllByCategoryAndDifficulty(
            @Param("chosenCategoryId") Integer chosenCategoryId, @Param("difficulty") String difficulty
    );
}