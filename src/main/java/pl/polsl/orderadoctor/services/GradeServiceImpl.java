package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.orderadoctor.dto.GradeDto;
import pl.polsl.orderadoctor.mappers.GradeMapper;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.Grade;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.repositories.DoctorRepository;
import pl.polsl.orderadoctor.repositories.GradeRepository;
import pl.polsl.orderadoctor.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final GradeMapper gradeMapper;

    @Override
    public void save(Grade grade) {
        gradeRepository.save(grade);
    }

    @Override
    public void saveAll(List<Grade> gradeList) {
        gradeRepository.saveAll(gradeList);
    }

    @Override
    public Grade findById(Long id) {
        Grade grade = gradeRepository.findById(id).get();
        return grade;
    }

    @Override
    public GradeDto findDtoById(Long id) {

        return gradeMapper.gradeToGradeDto(findById(id));
    }

    @Override
    @Transactional
    public GradeDto saveDto(GradeDto dto, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(dto.getDoctorId());

        if (!optionalUser.isPresent()) {
            log.error("User not found for id: " + userId);
            return new GradeDto();
        }

        if (!optionalDoctor.isPresent()) {
            log.error("Doctor not found for id: " + dto.getDoctorId());
            return new GradeDto();
        }

        User user = optionalUser.get();
        Doctor doctor = optionalDoctor.get();
        Optional<Grade> gradeOptional = user
                .getGrades()
                .stream()
                .filter(grade -> grade.getId().equals(dto.getId()))
                .findFirst();


        if (gradeOptional.isPresent()) {
            user.getGrades().remove(gradeOptional.get());
            doctor.getGrades().remove(gradeOptional.get());
        }

        Grade grade = gradeMapper.gradeDtoToGrade(dto);
        user.addGrade(grade);
        doctor.addGrade(grade);
        userRepository.save(user);
        doctorRepository.save(doctor);
        return dto;
    }


}
