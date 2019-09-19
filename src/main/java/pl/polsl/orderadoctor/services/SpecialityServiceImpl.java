package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.dto.SpecialityDto;
import pl.polsl.orderadoctor.mappers.SpecialityMapper;
import pl.polsl.orderadoctor.model.Speciality;
import pl.polsl.orderadoctor.repositories.SpecialityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;
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


}
