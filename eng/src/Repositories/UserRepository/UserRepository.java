package Repositories.UserRepository;

import Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
    User getFirstByUsername(String username);
    Iterable<User> getAllByFirstNameAndLastName(String firstName, String lastString);
    Optional<User> getFirstByFirstNameAndLastName(String firstName, String lastName);
}
