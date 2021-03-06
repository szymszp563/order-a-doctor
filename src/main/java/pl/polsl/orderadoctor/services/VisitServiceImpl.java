package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.dto.VisitDto;
import pl.polsl.orderadoctor.mappers.VisitMapper;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.MedicalProduct;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.model.Visit;
import pl.polsl.orderadoctor.model.VisitState;
import pl.polsl.orderadoctor.repositories.DoctorRepository;
import pl.polsl.orderadoctor.repositories.MedicalProductRepository;
import pl.polsl.orderadoctor.repositories.UserRepository;
import pl.polsl.orderadoctor.repositories.VisitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final MedicalProductRepository medicalProductRepository;
    private final VisitMapper visitMapper;

    @Override
    public void save(Visit visit) {
        visitRepository.save(visit);
    }

    @Override
    public void saveAll(List<Visit> visits) {
        visitRepository.saveAll(visits);
    }

    @Override
    public Visit findById(Long id) {
        Visit visit = visitRepository.findById(id).get();
        return visit;
    }

    @Override
    public VisitDto saveDto(VisitDto dto, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(dto.getDoctorId());
        Optional<MedicalProduct> optionalMedicalProduct = medicalProductRepository.findById(dto.getMedicalProductIds()[0]);

        if(!optionalDoctor.isPresent() || !optionalUser.isPresent() || !optionalMedicalProduct.isPresent()){
            return new VisitDto();
        }else {
            User user = optionalUser.get();
            Doctor doctor = optionalDoctor.get();
            MedicalProduct medicalProduct = optionalMedicalProduct.get();

            Optional<Visit> optionalVisit = user
                    .getVisits()
                    .stream()
                    .filter(visit -> visit.getId().equals(dto.getId()))
                    .findFirst();

            if (optionalVisit.isPresent()){
                user.getVisits().remove(optionalVisit.get());
                doctor.getVisits().remove(optionalVisit.get());
                visitRepository.deleteById(optionalVisit.get().getId());
            }

            Visit visit = visitMapper.visitDtoToVisit(dto);

            String sDate = dto.getStartingDate();
            String sTime = dto.getHour();
            DateTimeFormatter df = DateTimeFormatter .ofPattern("d-MM-yyyy");
            LocalDate date = LocalDate.parse(sDate, df);
            DateTimeFormatter dt = DateTimeFormatter .ofPattern("H:mm");
            LocalTime time = LocalTime.parse(sTime, dt);
            LocalDateTime dateFrom = date.atTime(time);
            visit.setDateFrom(dateFrom);

            visit.addMedicalProduct(medicalProduct);
            visit.setId(null);
            user.addVisit(visit);
            doctor.addVisit(visit);

//            userRepository.save(user);
//            doctorRepository.save(doctor);
            visitRepository.save(visit);
            return dto;
        }

    }

    @Override
    public void confirmVisit(Long visitId) {
        Optional<Visit> visit = visitRepository.findById(visitId);
        if(visit.isPresent()){
            visit.get().setVisitState(VisitState.ZATWIERDZONA);
            visitRepository.save(visit.get());
        }
    }

    @Override
    public void rateVisit(Long visitId) {
        Optional<Visit> visit = visitRepository.findById(visitId);
        if(visit.isPresent()){
            visit.get().setVisitState(VisitState.OCENIONA);
            visitRepository.save(visit.get());
        }
    }

    @Override
    public VisitDto findDtoById(Long visitId) {
        return visitMapper.visitToVisitDto(visitRepository.findById(visitId).get());
    }

    @Override
    public void deleteVitById(Long id) {
        Visit visit = visitRepository.findById(id).get();
        Doctor doctor = doctorRepository.findById(visit.getDoctor().getId()).get();
        User user = userRepository.findById(visit.getUser().getId()).get();
        doctor.getVisits().remove(visit);
        user.getVisits().remove(visit);
        userRepository.save(user);
        doctorRepository.save(doctor);
        visitRepository.delete(visit);
    }

}
