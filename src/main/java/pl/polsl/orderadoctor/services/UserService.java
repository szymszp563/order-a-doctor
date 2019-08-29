package pl.polsl.orderadoctor.services;

import pl.polsl.orderadoctor.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void saveAll(List<User> users);
    User findById(Long id);
}
