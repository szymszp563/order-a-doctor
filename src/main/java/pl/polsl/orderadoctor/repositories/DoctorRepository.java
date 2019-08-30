package pl.polsl.orderadoctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.Speciality;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByExternalIdAndAccountType(String externalId, AccountType accountType);

    Doctor findByExternalId(String externalId);

    List<Doctor> findAllBySpecialitiesAndCity(Speciality speciality, String city);
}
