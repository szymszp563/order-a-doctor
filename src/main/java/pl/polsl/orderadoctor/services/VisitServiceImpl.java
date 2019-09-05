package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.model.Visit;
import pl.polsl.orderadoctor.repositories.VisitRepository;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

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
}
