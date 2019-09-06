package pl.polsl.orderadoctor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.orderadoctor.model.AccountType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDto {

    private Long id;

    private AccountType accountType;

    private String externalId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String city;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String street;

    private String workingFrom;

    private String workingTo;

    private Byte[] image;

    private Double averageGrade;

    @NotBlank
    private String about;

    private List<VisitDto> visits = new LinkedList<>();

    private List<SpecialityDto> specialities = new LinkedList<>();

    private List<MedicalProductDto> medicalProducts = new LinkedList<>();

    private Set<GradeDto> grades = new HashSet<>();
}
