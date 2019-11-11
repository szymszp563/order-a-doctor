package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.orderadoctor.dto.DoctorDto;
import pl.polsl.orderadoctor.mappers.DoctorMapper;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.City;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.MedicalProduct;
import pl.polsl.orderadoctor.model.Speciality;
import pl.polsl.orderadoctor.model.VisitState;
import pl.polsl.orderadoctor.repositories.CityRepository;
import pl.polsl.orderadoctor.repositories.DoctorRepository;
import pl.polsl.orderadoctor.repositories.MedicalProductRepository;
import pl.polsl.orderadoctor.repositories.SpecialityRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialityRepository specialityRepository;
    private final MedicalProductRepository medicalProductRepository;
    private final CityRepository cityRepository;
    private final GradeService gradeService;
    private final DoctorMapper doctorMapper;

    @Override
    public void save(Doctor doctor) {
        if(!cityRepository.findByName(doctor.getCity()).isPresent())
            cityRepository.save(City.builder().name(doctor.getCity()).build());
        doctorRepository.save(doctor);
    }

    @Override
    public void saveAll(List<Doctor> doctorList) {
        doctorList.forEach(d->{
            if(!cityRepository.findByName(d.getCity()).isPresent())
                cityRepository.save(City.builder().name(d.getCity()).build());
        });
        doctorRepository.saveAll(doctorList);
    }

    @Override
    public Doctor findById(Long id) {
        Doctor doctor = doctorRepository.findById(id).get();

        return doctor;

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

        List<Speciality> specialities = specialityRepository.findByDoctorId(dto.getId());

        if(specialities.size()>0){
            detachedDoctor.setSpecialities(specialities);
        }

        Doctor savedDoctor = doctorRepository.save(detachedDoctor);
        if(!cityRepository.findByName(savedDoctor.getCity()).isPresent())
            cityRepository.save(City.builder().name(savedDoctor.getCity()).build());
        log.debug("Saved user: " + savedDoctor.getId());

        return doctorMapper.doctorToDoctorDto(savedDoctor);

    }

    @Override
    public DoctorDto findDtoById(Long id) {

        return doctorMapper.doctorToDoctorDto(findById(id));
    }

    @Override
    public List<DoctorDto> findAllDoctorsDto() {
        List<Doctor> doctors = doctorRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
        return doctors.stream().map(doctorMapper::doctorToDoctorDto).collect(Collectors.toList());
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        try {
            Doctor doctor = doctorRepository.findById(id).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;

            for (byte b : file.getBytes()) {
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
        MedicalProduct medicalProduct = medicalProductRepository.findById(id).get();
        doctor.getMedicalProducts().remove(medicalProduct);
        doctorRepository.save(doctor);
        medicalProductRepository.delete(medicalProduct);
    }

    @Override
    public List<DoctorDto> findAllDtoBySpeciality(Long id) {
        List<Doctor> doctors = doctorRepository.findAllBySpeciality(id);

        return doctors.stream().map(doctorMapper::doctorToDoctorDto).collect(Collectors.toList());
    }

    @Override
    public List<DoctorDto> findAllBySpecialityAndCity(Long id, String city) {

        List<Doctor> doctors = doctorRepository.findAllBySpecialityAndCity(id, city);

        return doctors.stream().map(doctorMapper::doctorToDoctorDto).collect(Collectors.toList());

    }

    @Override
    public void endPastVisits(Long id) {

        Doctor doctor = doctorRepository.findById(id).get();

        doctor.getVisits().stream().forEach(v -> {
            if(v.getDateFrom().isBefore(LocalDateTime.now())){
                v.setVisitState(VisitState.ENDED);
            }
        });
        doctorRepository.save(doctor);
    }


}
