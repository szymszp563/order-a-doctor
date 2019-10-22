package pl.polsl.orderadoctor.services;

import pl.polsl.orderadoctor.dto.SpecialityDto;
import pl.polsl.orderadoctor.model.Speciality;

import java.util.List;

public interface SpecialityService {
    void save(Speciality speciality);

    void saveAll(List<Speciality> specialities);

    Speciality findById(Long id);

    List<SpecialityDto> findAllSpecialitiesDto();

    SpecialityDto saveDto(SpecialityDto dto, Long doctorId);

    SpecialityDto findDtoById(Long id);

    List<Speciality> findByDoctorId(Long doctorId);
}
