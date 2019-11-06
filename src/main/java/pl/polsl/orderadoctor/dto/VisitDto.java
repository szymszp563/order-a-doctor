package pl.polsl.orderadoctor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.orderadoctor.model.VisitState;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class VisitDto {

    private Long id;

    private String description;

    private LocalDateTime dateFrom;

    private LocalDateTime dateTo;

    private VisitState visitState;

    private Long[] medicalProductIds;

    @NotBlank
    private String startingDate;

    private String hour;

    private Long doctorId;

//    private Integer month;
//
//    private Integer day;

}
