package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.model.Speciality;
import pl.polsl.orderadoctor.repositories.SpecialityRepository;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;

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
}
