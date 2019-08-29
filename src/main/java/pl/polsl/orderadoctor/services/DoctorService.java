package pl.polsl.orderadoctor.services;

import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.Speciality;

import java.util.List;

public interface DoctorService {

    void save(Doctor doctor);
    void saveAll(List<Doctor> doctorList);
    Doctor findById(Long id);
    List<Doctor> findAllBySpecialitiesAndCity(Speciality speciality, String city);
}
