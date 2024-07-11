package uz.muhammadtrying.pdpquizprojectbackend.mappers;

import org.mapstruct.*;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDTO userDTO, @MappingTarget User user);
}