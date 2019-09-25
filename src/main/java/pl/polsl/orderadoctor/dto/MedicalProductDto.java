package pl.polsl.orderadoctor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class MedicalProductDto {
    private Long id;

    @NotBlank
    private String name;

    @Positive
    private Double price;

    @NotBlank
    private String duration;

    private String description;

//    private DoctorInfo doctorInfo;
}
