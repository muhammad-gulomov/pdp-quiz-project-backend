package uz.muhammadtrying.pdpquizprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.muhammadtrying.pdpquizprojectbackend.entity.QuestionList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    private Integer questionListId;
    private Integer correctAnswers;
    private Integer incorrectAnswers;
}
