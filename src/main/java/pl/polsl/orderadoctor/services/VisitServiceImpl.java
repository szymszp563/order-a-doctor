package pl.polsl.orderadoctor.services;

import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.model.Visit;
import pl.polsl.orderadoctor.repositories.VisitRepository;

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

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
