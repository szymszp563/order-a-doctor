package pl.polsl.orderadoctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.orderadoctor.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
