package pl.polsl.orderadoctor.mappers;

import org.mapstruct.Mapper;
import pl.polsl.orderadoctor.dto.SpecialityDto;
import pl.polsl.orderadoctor.model.Speciality;

@Mapper(componentModel = "spring")
public interface SpecialityMapper {

    Speciality specialityDtoToSpeciality(SpecialityDto dto);

    SpecialityDto specialityToSpecialityDto(Speciality entity);
}
