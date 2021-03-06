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
public class UserDto {

    private Long id;

    private AccountType accountType;

    private String externalId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String city;

    private String phoneNumber;

    @Email
    @NotBlank
    private String email;

    private Byte[] image;

    private List<VisitDto> visits = new LinkedList<>();

    private Set<GradeDto> grades = new HashSet<>();
}
