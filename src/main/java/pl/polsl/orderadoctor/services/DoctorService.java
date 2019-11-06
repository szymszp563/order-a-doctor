package pl.polsl.orderadoctor.services;

import org.springframework.web.multipart.MultipartFile;
import pl.polsl.orderadoctor.dto.DoctorDto;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.Doctor;

import java.util.List;

public interface DoctorService {

    void save(Doctor doctor);

    void saveAll(List<Doctor> doctorList);

    Doctor findById(Long id);

    Doctor findByExternalIdAndAccountType(String externalId, AccountType accountType);

    Doctor findByExternalId(String externalId);

    DoctorDto saveDto(DoctorDto dto);

    DoctorDto findDtoById(Long id);

    List<DoctorDto> findAllDoctorsDto();

    void saveImageFile(Long id, MultipartFile file);

    void deleteSpecialityById(Long doctorId, Long id);

    void deleteMedicalProductById(Long doctorId, Long id);

    List<DoctorDto> findAllDtoBySpeciality(Long id);

    List<DoctorDto> findAllBySpecialityAndCity(Long id, String city);

    void endPastVisits(Long id);
}
