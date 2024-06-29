package uz.muhammadtrying.pdpquizprojectbackend.entity;

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
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    @ManyToOne
    private Module module;
    @Enumerated(EnumType.STRING)
    private DifficultyEnum difficulty;
}