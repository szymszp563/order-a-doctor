package pl.polsl.orderadoctor.services;

import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.model.Speciality;
import pl.polsl.orderadoctor.repositories.SpecialityRepository;

import java.util.List;

@Service
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

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
