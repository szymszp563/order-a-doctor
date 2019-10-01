package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.orderadoctor.dto.MedicalProductDto;
import pl.polsl.orderadoctor.mappers.MedicalProductMapper;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.MedicalProduct;
import pl.polsl.orderadoctor.repositories.DoctorRepository;
import pl.polsl.orderadoctor.repositories.MedicalProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MedicalProductServiceImpl implements MedicalProductService {

    private final MedicalProductRepository medicalProductRepository;
    private final DoctorRepository doctorRepository;
    private final MedicalProductMapper medicalProductMapper;

    @Override
    public void save(MedicalProduct medicalProduct) {
        medicalProductRepository.save(medicalProduct);
    }

    @Override
    public void saveAll(List<MedicalProduct> medicalProducts) {
        medicalProductRepository.saveAll(medicalProducts);

    }

    @Override
    public MedicalProduct findById(Long id) {
        MedicalProduct medicalProduct = medicalProductRepository.findById(id).get();
        return medicalProduct;
    }

    @Override
    public List<MedicalProductDto> findAllSMedicalProductsDto() {

        List<MedicalProduct> products = medicalProductRepository
                .findAll(Sort.by(Sort.Direction.ASC, "name"));
        return products.stream().map(medicalProductMapper::medicalProductToMedicalProductDto).collect(Collectors.toList());
    }

    @Override
    public MedicalProductDto findDtoById(Long id) {
        return medicalProductMapper.medicalProductToMedicalProductDto(medicalProductRepository.findById(id).get());
    }

    @Override
    @Transactional
    public MedicalProductDto saveDto(MedicalProductDto dto, Long doctorId) {

        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (!doctorOptional.isPresent()) {
            log.error("Doctor not found for id: " + doctorId);
            return new MedicalProductDto();
        } else {
            Doctor doctor = doctorOptional.get();

            //checks if medical product exists in database (same id)
            Optional<MedicalProduct> medicalProductOptional = doctor
                    .getMedicalProducts()
                    .stream()
                    .filter(product -> product.getId().equals(dto.getId()))
                    .findFirst();

            //checks if medical product with the same name exists in database
            Optional<MedicalProduct> medicalProductOptional2 = doctor
                    .getMedicalProducts()
                    .stream()
                    .filter(product -> product.getName().equals(dto.getName()))
                    .findFirst();

            if (medicalProductOptional.isPresent() || medicalProductOptional2.isPresent()) {

                if (medicalProductOptional.isPresent() && medicalProductOptional2.isPresent()) {
                    doctor.getMedicalProducts().remove(medicalProductOptional.get());
                    doctor.getMedicalProducts().remove(medicalProductOptional2.get());
                } else if (medicalProductOptional.isPresent())
                    doctor.getMedicalProducts().remove(medicalProductOptional.get());
                else
                    doctor.getMedicalProducts().remove(medicalProductOptional2.get());
            }

            MedicalProduct medicalProduct = medicalProductMapper.medicalProductDtoToMedicalProduct(dto);
            doctor.addMedicalProduct(medicalProduct);
            doctorRepository.save(doctor);
            return dto;
        }

    }
}
