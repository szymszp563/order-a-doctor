package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.orderadoctor.dto.DoctorDto;
import pl.polsl.orderadoctor.mappers.DoctorMapper;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.Speciality;
import pl.polsl.orderadoctor.repositories.DoctorRepository;
import pl.polsl.orderadoctor.repositories.MedicalProductRepository;
import pl.polsl.orderadoctor.repositories.SpecialityRepository;

import java.io.IOException;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialityRepository specialityRepository;
    private final MedicalProductRepository medicalProductRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    public void saveAll(List<Doctor> doctorList) {
        doctorRepository.saveAll(doctorList);
    }

    @Override
    public Doctor findById(Long id) {
        Doctor doctor = doctorRepository.findById(id).get();

        return doctor;

    }

    @Override
    public List<Doctor> findAllBySpecialitiesAndCity(Speciality speciality, String city) {
        List<Doctor> doctors;
        doctors = doctorRepository.findAllBySpecialitiesAndCity(speciality, city);
        return doctors;
    }

    @Override
    public Doctor findByExternalIdAndAccountType(String externalId, AccountType accountType) {
        Doctor doctor = doctorRepository.findByExternalIdAndAccountType(externalId, accountType);
        return doctor;
    }

    @Override
    public Doctor findByExternalId(String externalId) {
        Doctor doctor = doctorRepository.findByExternalId(externalId);
        return doctor;
    }

    @Override
    @Transactional
    public DoctorDto saveDto(DoctorDto dto) {

        Doctor detachedDoctor = doctorMapper.doctorDtoToDoctor(dto);

        Doctor savedDoctor = doctorRepository.save(detachedDoctor);
        log.debug("Saved user: " + savedDoctor.getId());

        return doctorMapper.doctorToDoctorDto(savedDoctor);

    }

    @Override
    public DoctorDto findDtoById(Long id) {

        return doctorMapper.doctorToDoctorDto(findById(id));
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        try {
            Doctor doctor = doctorRepository.findById(id).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            doctor.setImage(byteObjects);

            doctorRepository.save(doctor);
        } catch (IOException e) {
            log.error("Error occurred during uploading image", e);

            e.printStackTrace();
        }
    }

    @Override
    public void deleteSpecialityById(Long doctorId, Long id) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        doctor.getSpecialities().remove(specialityRepository.findById(id).get());
        doctorRepository.save(doctor);
    }

    @Override
    public void deleteMedicalProductById(Long doctorId, Long id) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        doctor.getMedicalProducts().remove(medicalProductRepository.findById(id).get());
        doctorRepository.save(doctor);
    }

}
