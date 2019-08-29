package pl.polsl.orderadoctor.mappers;

import org.mapstruct.Mapper;
import pl.polsl.orderadoctor.dto.MedicalProductDto;
import pl.polsl.orderadoctor.model.MedicalProduct;

@Mapper(componentModel = "spring")
public interface MedicalProductMapper {

    MedicalProduct medicalProductDtoToMedicalProduct(MedicalProductDto dto);

    MedicalProductDto medicalProductToMedicalProductDto(MedicalProduct entity);
}
