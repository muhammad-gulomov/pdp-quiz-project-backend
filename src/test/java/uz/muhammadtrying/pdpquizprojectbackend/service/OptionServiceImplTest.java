package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Question;
import uz.muhammadtrying.pdpquizprojectbackend.repo.OptionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OptionServiceImplTest {

    private OptionServiceImpl optionService;
    private OptionRepository optionRepository;

    @BeforeEach
    public void setUp() {
        this.optionRepository = Mockito.mock(OptionRepository.class);
        this.optionService = new OptionServiceImpl(optionRepository);
    }

    @Test
    void save() {
        Option option = new Option("Test Content", false, new Question());

        Mockito.when(optionRepository.save(Mockito.any())).thenReturn(option);

        Option savedOption = optionService.save(option);
        assertEquals(option.getOptionContent(), savedOption.getOptionContent());
    }

    @Test
    void findById() {
        Option option = new Option("Test Content", false, new Question());

        Mockito.when(optionRepository.findById(Mockito.any())).thenReturn(Optional.of(option));

        Optional<Option> optionOpt = optionService.findById(1);

        assertTrue(optionOpt.isPresent());
        assertEquals(option.getOptionContent(), optionOpt.get().getOptionContent());
    }

    @Test
    void findByIdNotPresent() {
        Mockito.when(optionRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Optional<Option> optionOpt = optionService.findById(1);

        assertFalse(optionOpt.isPresent());
    }
}