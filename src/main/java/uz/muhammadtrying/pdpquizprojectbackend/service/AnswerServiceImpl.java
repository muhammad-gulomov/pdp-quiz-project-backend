package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.AnswerService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AnswerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Override
    public void saveAll(List<Answer> answers) {
        answerRepository.saveAll(answers);
    }

    @Override
    public Optional<Answer> findByChosenOption(Option chosenOption) {
        return answerRepository.findAnswerByChosenOption(chosenOption);
    }

    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);
    }
}
