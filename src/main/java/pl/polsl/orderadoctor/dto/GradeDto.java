package pl.polsl.orderadoctor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.orderadoctor.model.DegreeType;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class GradeDto {

    private Long id;

    @NotBlank
    private String comment;

    private Byte grade;

    private String doctorFirstName;

    private String doctorLastName;

    private Long doctorId;

    private DegreeType doctorDegree;

    private String userLastName;

    private Long userId;

    private String userFirstName;

    private Long visitId;

}
