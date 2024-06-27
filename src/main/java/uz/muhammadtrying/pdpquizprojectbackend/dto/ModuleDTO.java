package uz.muhammadtrying.pdpquizprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Question;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModuleDTO {
    private Integer id;
    private String name;
    private List<QuestionList> questionLists;
    private List<Question> questions;
}
