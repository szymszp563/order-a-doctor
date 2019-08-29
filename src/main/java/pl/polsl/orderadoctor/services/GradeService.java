package pl.polsl.orderadoctor.services;

import pl.polsl.orderadoctor.model.Grade;

import java.util.List;

public interface GradeService {
    void save(Grade grade);
    void saveAll(List<Grade> gradeList);
    Grade findById(Long id);
}
