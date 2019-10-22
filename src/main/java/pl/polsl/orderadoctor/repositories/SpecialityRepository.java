package pl.polsl.orderadoctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.polsl.orderadoctor.model.Speciality;

import java.util.List;
import java.util.Optional;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    Optional<Speciality> findByDescription(String description);

    @Query("select s from Speciality s join s.doctors sd where sd.id = ?1")
    List<Speciality> findByDoctorId(Long doctorId);

}
