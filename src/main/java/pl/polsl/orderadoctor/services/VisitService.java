package pl.polsl.orderadoctor.services;

import pl.polsl.orderadoctor.model.Visit;

import java.util.List;

public interface VisitService {
    void save(Visit visit);
    void saveAll(List<Visit> visits);
    Visit findById(Long id);
}