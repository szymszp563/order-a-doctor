package pl.polsl.orderadoctor.services;

import org.springframework.web.multipart.MultipartFile;
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

    UserDto findDtoById(Long id);

    void saveImageFile(Long id, MultipartFile file);

    void deleteGrade(Long id);

    void endPastVisits(Long id);
}
