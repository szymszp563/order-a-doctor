package pl.polsl.orderadoctor.services;

import pl.polsl.orderadoctor.dto.VisitDto;
import pl.polsl.orderadoctor.model.Visit;

import java.util.List;

public interface VisitService {
    void save(Visit visit);

    void saveAll(List<Visit> visits);

    Visit findById(Long id);

    VisitDto saveDto(VisitDto dto, Long userId);

    void confirmVisit(Long visitId);

    void rateVisit(Long visitId);

    VisitDto findDtoById(Long visitId);

    void deleteVitById(Long id);

}
