package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.orderadoctor.dto.SpecialityDto;
import pl.polsl.orderadoctor.mappers.SpecialityMapper;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.Speciality;
import pl.polsl.orderadoctor.repositories.DoctorRepository;
import pl.polsl.orderadoctor.repositories.SpecialityRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final DoctorRepository doctorRepository;
    private final SpecialityMapper specialityMapper;

    @Override
    public void save(Speciality speciality) {
        specialityRepository.save(speciality);
    }

    @Override
    public void saveAll(List<Speciality> specialities) {
        specialityRepository.saveAll(specialities);
    }

    @Override
    public Speciality findById(Long id) {
        Speciality speciality = specialityRepository.findById(id).get();
        return speciality;

    }

    @Override
    public List<SpecialityDto> findAllSpecialitiesDto() {
        List<Speciality> specialities = specialityRepository
                .findAll(Sort.by(Sort.Direction.ASC, "description"));
        return specialities.stream().map(specialityMapper::specialityToSpecialityDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SpecialityDto saveDto(SpecialityDto dto, Long doctorId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (!doctorOptional.isPresent()) {
            log.error("Doctor not found for id: " + doctorId);
            return new SpecialityDto();
        } else {
            Doctor doctor = doctorOptional.get();

            Optional<Speciality> specialityOptional = doctor
                    .getSpecialities()
                    .stream()
                    .filter(speciality -> speciality.getId().equals(dto.getId()))
                    .findFirst();

            Optional<Speciality> specialityOptional2 = doctor
                    .getSpecialities()
                    .stream()
                    .filter(speciality -> speciality.getDescription().equals(dto.getDescription()))
                    .findFirst();

            Optional<Speciality> speciality = specialityRepository.findByDescription(dto.getDescription());
            if (specialityOptional.isPresent() || specialityOptional2.isPresent()) {

                if (specialityOptional.isPresent() && specialityOptional2.isPresent()) {
                    doctor.getSpecialities().remove(specialityOptional.get());
                    doctor.getSpecialities().remove(specialityOptional2.get());
                } else if (specialityOptional.isPresent())
                    doctor.getSpecialities().remove(specialityOptional.get());
                else
                    doctor.getSpecialities().remove(specialityOptional2.get());
            }

            speciality.ifPresent(value -> doctor.getSpecialities().add(value));
            doctorRepository.save(doctor);
            return specialityMapper.specialityToSpecialityDto(speciality.get());
        }
    }

    @Override
    public SpecialityDto findDtoById(Long id) {
        return specialityMapper.specialityToSpecialityDto(specialityRepository.findById(id).get());
    }

    @Override
    public List<Speciality> findByDoctorId(Long doctorId) {
        return specialityRepository.findByDoctorId(doctorId);

    }


}
