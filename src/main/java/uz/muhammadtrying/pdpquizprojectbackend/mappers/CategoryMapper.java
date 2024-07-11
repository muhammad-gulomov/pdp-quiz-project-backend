package uz.muhammadtrying.pdpquizprojectbackend.mappers;

import org.mapstruct.*;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CategoryDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Category;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryDTO categoryDTO, @MappingTarget Category category);
}