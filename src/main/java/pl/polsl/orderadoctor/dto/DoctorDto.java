package pl.polsl.orderadoctor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.orderadoctor.model.AccountType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDto {

    private Long id;

    private AccountType accountType;

    private String externalId;

    private String firstName;

    private String lastName;

    @NotBlank
    private String city;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String street;

    @NotBlank
    private String workingHoursFrom;

    @NotBlank
    private String workingHoursTo;

    private Byte[] image;

    @NotBlank
    private String about;

    private List<VisitDto> visits = new LinkedList<>();

    private List<SpecialityDto> specialities = new LinkedList<>();

    private List<MedicalProductDto> medicalProducts = new LinkedList<>();
}
