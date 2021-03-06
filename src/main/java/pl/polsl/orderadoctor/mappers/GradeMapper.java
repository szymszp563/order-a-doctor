package pl.polsl.orderadoctor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.polsl.orderadoctor.dto.GradeDto;
import pl.polsl.orderadoctor.model.Grade;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @Mappings({
            @Mapping(target = "doctorFirstName", source = "doctor.firstName"),
            @Mapping(target = "doctorLastName", source = "doctor.lastName"),
            @Mapping(target = "doctorId", source = "doctor.id"),
            @Mapping(target = "doctorDegree", source = "doctor.degree"),
            @Mapping(target = "userFirstName", source = "user.firstName"),
            @Mapping(target = "userLastName", source = "user.lastName"),
            @Mapping(target = "userId", source = "user.id")
    })
    GradeDto gradeToGradeDto(Grade entity);

    Grade gradeDtoToGrade(GradeDto dto);
}
