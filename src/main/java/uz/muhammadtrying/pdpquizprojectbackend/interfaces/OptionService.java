package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;

import java.util.Optional;

@Service
public interface OptionService {
    Option save(Option option);

    Optional<Option> findById(Integer id);
}
