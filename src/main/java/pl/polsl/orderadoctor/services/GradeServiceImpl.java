package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.dto.GradeDto;
import pl.polsl.orderadoctor.mappers.GradeMapper;
import pl.polsl.orderadoctor.model.Grade;
import pl.polsl.orderadoctor.repositories.GradeRepository;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
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
}
