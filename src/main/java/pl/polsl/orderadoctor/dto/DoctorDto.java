package pl.polsl.orderadoctor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.orderadoctor.model.AccountType;

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

    private String city;

    private String email;

    private String street;

    private String workingHoursFrom;

    private String workingHoursTo;

    private Byte[] image;

    private String about;

    private List<VisitDto> visits = new LinkedList<>();

    private List<SpecialityDto> specialities = new LinkedList<>();

    private List<MedicalProductDto> medicalProducts = new LinkedList<>();
}
