package pl.polsl.orderadoctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.orderadoctor.model.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
