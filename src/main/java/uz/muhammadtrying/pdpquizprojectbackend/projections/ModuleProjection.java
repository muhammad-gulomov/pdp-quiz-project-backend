package uz.muhammadtrying.pdpquizprojectbackend.projections;

import uz.muhammadtrying.pdpquizprojectbackend.entity.Question;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;

import java.util.List;

public interface ModuleProjection {
    Integer getId();

    String getName();

    List<QuestionList> getQuestionLists();

    List<Question> getQuestions();
}
