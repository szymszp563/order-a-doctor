package pl.polsl.orderadoctor.services;

import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
