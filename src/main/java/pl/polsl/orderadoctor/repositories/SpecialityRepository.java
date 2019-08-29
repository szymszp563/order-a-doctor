package pl.polsl.orderadoctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.orderadoctor.model.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
}
