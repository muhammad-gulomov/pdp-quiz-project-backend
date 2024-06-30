package uz.muhammadtrying.pdpquizprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryStatDTO {
    private Integer id;
    private String name;
    private String photo;

    private Integer modulesFinished;

    private Integer easyQuestionListCount;
    private Integer mediumQuestionListCount;
    private Integer hardQuestionListCount;
}
