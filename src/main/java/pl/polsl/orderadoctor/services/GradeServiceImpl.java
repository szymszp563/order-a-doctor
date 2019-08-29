package pl.polsl.orderadoctor.services;

import pl.polsl.orderadoctor.model.Grade;
import pl.polsl.orderadoctor.repositories.GradeRepository;

import java.util.List;

public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

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
}
