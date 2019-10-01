package pl.polsl.orderadoctor.services;

import org.springframework.web.multipart.MultipartFile;
import pl.polsl.orderadoctor.dto.DoctorDto;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.Speciality;

import java.util.List;

public interface DoctorService {

    void save(Doctor doctor);

    void saveAll(List<Doctor> doctorList);

    Doctor findById(Long id);

    List<Doctor> findAllBySpecialitiesAndCity(Speciality speciality, String city);

    Doctor findByExternalIdAndAccountType(String externalId, AccountType accountType);

    Doctor findByExternalId(String externalId);

    DoctorDto saveDto(DoctorDto dto);

    DoctorDto findDtoById(Long id);

    List<DoctorDto> findAllDoctorsDto();

    void saveImageFile(Long id, MultipartFile file);

    void deleteSpecialityById(Long doctorId, Long id);

    void deleteMedicalProductById(Long doctorId, Long id);

}
