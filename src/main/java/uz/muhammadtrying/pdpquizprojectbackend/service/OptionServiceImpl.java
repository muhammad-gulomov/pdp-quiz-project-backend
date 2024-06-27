package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.OptionService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.OptionRepository;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
    private final OptionRepository optionRepository;

    @Override
    public void save(Option option) {
        optionRepository.save(option);
    }
}
