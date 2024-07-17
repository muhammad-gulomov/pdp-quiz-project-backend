package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.OptionService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.OptionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
    private final OptionRepository optionRepository;

    @Override
    public Option save(Option option) {
        return optionRepository.save(option);
    }

    @Override
    public Optional<Option> findById(Integer id) {
        return optionRepository.findById(id);
    }
}
