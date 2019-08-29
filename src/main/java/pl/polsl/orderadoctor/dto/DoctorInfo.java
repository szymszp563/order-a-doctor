package pl.polsl.orderadoctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.orderadoctor.model.AccountType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DoctorInfo {
    private Long id;

    private AccountType accountType;

    private String externalId;

    private String firstName;

    private String lastName;

    private String city;

    private String email;

    private String street;

    private LocalDateTime workingHoursFrom;

    private LocalDateTime workingHoursTo;

    private Byte[] image;

    private String about;
}
