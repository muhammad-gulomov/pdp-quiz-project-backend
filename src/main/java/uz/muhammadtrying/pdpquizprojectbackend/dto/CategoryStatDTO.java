package uz.muhammadtrying.pdpquizprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryStatDTO {
    private Integer id;
    private String name;
    private Integer attachmentId;

    private Integer modulesFinished;

    private Map<String, Integer> easyQuestionList;
    private Map<String, Integer> mediumQuestionList;
    private Map<String, Integer> hardQuestionList;
}
