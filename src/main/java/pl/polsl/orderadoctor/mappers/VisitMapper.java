package pl.polsl.orderadoctor.mappers;

import org.mapstruct.Mapper;
import pl.polsl.orderadoctor.dto.VisitDto;
import pl.polsl.orderadoctor.model.Visit;

@Mapper(componentModel = "spring", uses = MedicalProductMapper.class)
public interface VisitMapper {

    Visit visitDtoToVisit(VisitDto dto);

    VisitDto visitToVisitDto(Visit entity);
}
