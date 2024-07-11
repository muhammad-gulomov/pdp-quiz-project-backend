package uz.muhammadtrying.pdpquizprojectbackend.mappers;

import org.mapstruct.*;
import uz.muhammadtrying.pdpquizprojectbackend.dto.OptionDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Option;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OptionMapper {
    Option toEntity(OptionDTO optionDTO);

    OptionDTO toDto(Option option);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Option partialUpdate(OptionDTO optionDTO, @MappingTarget Option option);
}