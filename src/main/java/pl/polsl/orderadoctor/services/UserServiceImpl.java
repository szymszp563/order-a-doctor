package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.orderadoctor.dto.UserDto;
import pl.polsl.orderadoctor.mappers.UserMapper;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.repositories.UserRepository;

import java.io.IOException;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

    @Override
    @Transactional
    public UserDto findDtoById(Long id) {
        return userMapper.userToUserDto(findById(id));
    }

    @Override
    @Transactional
    public void saveImageFile(Long id, MultipartFile file) {
        try {
            User user = userRepository.findById(id).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            user.setImage(byteObjects);

            userRepository.save(user);
        } catch (IOException e) {
            log.error("Error occurred during uploading image", e);

            e.printStackTrace();
        }
    }
}
