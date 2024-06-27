package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;

@Service
public interface OptionService {
    void save(Option option);
}
