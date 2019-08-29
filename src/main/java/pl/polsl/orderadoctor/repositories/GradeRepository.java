package pl.polsl.orderadoctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.orderadoctor.model.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
