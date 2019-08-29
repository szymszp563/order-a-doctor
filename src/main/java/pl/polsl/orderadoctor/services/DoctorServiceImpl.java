package pl.polsl.orderadoctor.services;

import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.Speciality;
import pl.polsl.orderadoctor.repositories.DoctorRepository;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    public void saveAll(List<Doctor> doctorList) {
        doctorRepository.saveAll(doctorList);
    }

    @Override
    public Doctor findById(Long id) {
        Doctor doctor = doctorRepository.findById(id).get();

        return doctor;

    }

    @Override
    public List<Doctor> findAllBySpecialitiesAndCity(Speciality speciality, String city) {
        List<Doctor> doctors;
        doctors = doctorRepository.findAllBySpecialitiesAndCity(speciality, city);
        return doctors;
    }

}
