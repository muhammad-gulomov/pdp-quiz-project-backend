package uz.muhammadtrying.pdpquizprojectbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "attempt")
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Timestamp date;

    @ManyToOne
    private User user;

    @ManyToOne
    private QuestionList questionList;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Answer> answers;

}