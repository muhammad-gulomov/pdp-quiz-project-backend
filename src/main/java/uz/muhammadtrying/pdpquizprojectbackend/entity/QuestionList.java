package uz.muhammadtrying.pdpquizprojectbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class QuestionList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JsonIgnore
    private Module module;
    @Enumerated(EnumType.STRING)
    private DifficultyEnum difficulty;
    @OneToMany(mappedBy = "questionList", fetch = FetchType.EAGER)
    private List<Question> questions;


}