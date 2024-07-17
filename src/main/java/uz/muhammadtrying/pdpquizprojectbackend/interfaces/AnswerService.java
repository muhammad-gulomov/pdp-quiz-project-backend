package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;

import java.util.Optional;

@Service
public interface AnswerService {

    Optional<Answer> findByChosenOption(Option chosenOption);

    Answer save(Answer answer);
}
