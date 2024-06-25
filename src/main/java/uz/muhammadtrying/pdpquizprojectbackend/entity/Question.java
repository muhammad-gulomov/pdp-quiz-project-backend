package uz.muhammadtrying.pdpquizprojectbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String questionContent;

    private Timestamp seconds;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL) // orphanRemoval = true
    private List<Option> options;

    @ManyToOne
    @JoinColumn(name = "question_list_id")
    private QuestionList questionList;

}