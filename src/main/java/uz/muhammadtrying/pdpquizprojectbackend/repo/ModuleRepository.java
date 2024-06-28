package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.projections.ModuleProjection;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    @Query(value = """
                select m.id as id, m.name as name, array_agg(ql) as questionLists, array_agg(q) as questions
                    from module m
                        join question_list ql on m.id = ql.module_id
                        join question q on ql.id = q.question_list_id
                    where m.category_id = ?1
                    group by m.id
            """, nativeQuery = true)
    List<ModuleProjection> findAllByCategoryAndDifficulty(
            @Param("chosenCategoryId") Integer chosenCategoryId
    );
}