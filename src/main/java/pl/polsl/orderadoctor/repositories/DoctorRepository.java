package pl.polsl.orderadoctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.Doctor;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByExternalIdAndAccountType(String externalId, AccountType accountType);

    Doctor findByExternalId(String externalId);

    @Query("SELECT d FROM Doctor d JOIN d.specialities ds where ds.id = ?1 order by d.averageGrade desc")
    List<Doctor> findAllBySpeciality(Long id);

    @Query("SELECT d FROM Doctor d JOIN d.specialities ds where ds.id = ?1 and d.city = ?2 order by d.averageGrade desc")
    List<Doctor> findAllBySpecialityAndCity(Long id, String city);
}
