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
public class UserDto {

    private Long id;

    private AccountType accountType;

    private String externalId;

    private String firstName;

    private String lastName;

    private String city;

    private String email;

    private Byte[] image;

    private List<VisitDto> visits = new LinkedList<>();
}
