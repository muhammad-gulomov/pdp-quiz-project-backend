package uz.muhammadtrying.pdpquizprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardDTO {
    private CategoryDTO categoryDTO;
    private Integer rank;
    private String photoUrl;
    private String fullName;
    private Integer score;
}