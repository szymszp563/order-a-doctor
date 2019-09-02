package pl.polsl.orderadoctor.services;

import pl.polsl.orderadoctor.dto.UserDto;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    void saveAll(List<User> users);

    User findById(Long id);

    User findByExternalIdAndAccountType(String externalId, AccountType accountType);

    User findByExternalId(String externalId);

    UserDto saveDto(UserDto dto);
}
