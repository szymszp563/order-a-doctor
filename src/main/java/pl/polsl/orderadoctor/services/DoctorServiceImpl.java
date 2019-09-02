package pl.polsl.orderadoctor.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.orderadoctor.dto.DoctorDto;
import pl.polsl.orderadoctor.mappers.DoctorMapper;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.Speciality;
import pl.polsl.orderadoctor.repositories.DoctorRepository;

import java.util.List;

@Log4j2
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

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

}
