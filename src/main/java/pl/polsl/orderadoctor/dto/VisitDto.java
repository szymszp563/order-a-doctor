package pl.polsl.orderadoctor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VisitDto {

    private Long id;

    private String description;

    private LocalDateTime dateFrom;

    private LocalDateTime dateTo;

    private List<MedicalProductDto> medicalProducts = new LinkedList<>();

//    private DoctorInfo doctorInfo;

//    private UserInfo userInfo;
}
