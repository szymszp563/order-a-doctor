package pl.polsl.orderadoctor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
public class MedicalProductDto {
    private Long id;

    private String name;

    private Double price;

    private Duration duration;

//    private DoctorInfo doctorInfo;
}
