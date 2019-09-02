package pl.polsl.orderadoctor.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.orderadoctor.dto.UserDto;
import pl.polsl.orderadoctor.mappers.UserMapper;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.repositories.UserRepository;

import java.util.List;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    @Override
    public User findByExternalIdAndAccountType(String externalId, AccountType accountType) {
        User user = userRepository.findByExternalIdAndAccountType(externalId, accountType);
        return user;
    }

    @Override
    public User findByExternalId(String externalId) {
        User user = userRepository.findByExternalId(externalId);
        return user;
    }

    @Override
    @Transactional
    public UserDto saveDto(UserDto dto) {
        User detachedUser = userMapper.userDtoToUser(dto);

        User savedUser = userRepository.save(detachedUser);
        log.debug("Saved user: " + savedUser.getId());

        return userMapper.userToUserDto(savedUser);

    }
}
