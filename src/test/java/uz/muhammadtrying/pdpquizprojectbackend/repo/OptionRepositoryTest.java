package uz.muhammadtrying.pdpquizprojectbackend.repo;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OptionRepositoryTest {

    @Autowired
    private OptionRepository optionRepository;

    @Test
    void save() {
        Option option = Option.builder().optionContent("Option A").isCorrect(true).build();
        Option savedOption = optionRepository.save(option);

        Optional<Option> optionOpt = optionRepository.findById(savedOption.getId());
        assertTrue(optionOpt.isPresent());
    }
}