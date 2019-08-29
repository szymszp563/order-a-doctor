package pl.polsl.orderadoctor.mappers;

import org.mapstruct.Mapper;
import pl.polsl.orderadoctor.dto.DoctorDto;
import pl.polsl.orderadoctor.model.Doctor;

@Mapper(componentModel = "spring", uses = {SpecialityMapper.class, MedicalProductMapper.class, VisitMapper.class})
public interface DoctorMapper {

    Doctor doctorDtoToDoctor(DoctorDto dto);

    DoctorDto doctorToDoctorDto(Doctor entity);


}
