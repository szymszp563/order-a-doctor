package pl.polsl.orderadoctor.services;

import pl.polsl.orderadoctor.dto.MedicalProductDto;
import pl.polsl.orderadoctor.model.MedicalProduct;

import java.util.List;

public interface MedicalProductService {
    void save(MedicalProduct medicalProduct);
    void saveAll(List<MedicalProduct> medicalProducts);
    MedicalProduct findById(Long id);

    List<MedicalProductDto> findAllSMedicalProductsDto();

    MedicalProductDto findDtoById(Long id);

    MedicalProductDto saveDto(MedicalProductDto dto, Long doctorId);
}
