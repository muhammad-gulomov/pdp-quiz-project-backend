package uz.muhammadtrying.pdpquizprojectbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String optionContent;

    private Boolean isCorrect;
    @ManyToOne
    @JsonIgnore
    private Question question;

    public Option(String optionContent, Boolean isCorrect, Question question) {
        this.optionContent = optionContent;
        this.isCorrect = isCorrect;
        this.question = question;
    }
}