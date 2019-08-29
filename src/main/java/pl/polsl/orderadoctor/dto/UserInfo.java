package pl.polsl.orderadoctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.orderadoctor.model.AccountType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserInfo {

    private Long id;

    private AccountType accountType;

    private String externalId;

    private String firstName;

    private String lastName;

    private String city;

    private String email;

    private Byte[] image;
}
