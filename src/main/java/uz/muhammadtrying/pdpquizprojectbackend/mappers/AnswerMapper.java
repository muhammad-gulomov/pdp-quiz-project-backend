package uz.muhammadtrying.pdpquizprojectbackend.mappers;

import org.mapstruct.*;
import uz.muhammadtrying.pdpquizprojectbackend.dto.AnswerDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Answer;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnswerMapper {
    Answer toEntity(AnswerDTO answerDTO);

    AnswerDTO toDto(Answer answer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Answer partialUpdate(AnswerDTO answerDTO, @MappingTarget Answer answer);
}