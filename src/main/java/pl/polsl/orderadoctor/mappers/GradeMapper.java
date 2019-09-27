package pl.polsl.orderadoctor.mappers;

import org.mapstruct.Mapper;
import pl.polsl.orderadoctor.dto.GradeDto;
import pl.polsl.orderadoctor.model.Grade;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    GradeDto gradeToGradeDto(Grade entity);

    Grade gradeDtoToGrade(GradeDto dto);
}
