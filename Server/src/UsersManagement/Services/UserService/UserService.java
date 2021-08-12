package UsersManagement.Services.UserService;

import Entities.User.User;
import Repositories.UserRepository.UserRepository;
import UsersManagement.UserRegistrationDetails.UserRegistrationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public User registerNewUserAccount(UserRegistrationDetails userData) {
        if (checkIfUserExist(userData.getEmail())) {
           // throw new EmailExistsException(
                   // "There is an account with that email adress:" + accountDto.getEmail());
        }
        User user = new User();
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());

        user.setPassword(passwordEncoder.encode(userData.getPassword()));

        user.setUsername(userData.getEmail());
        return userRepository.save(user);
    }
    public boolean checkIfUserExist(String email) {
        return userRepository.findByUsername(email) !=null ? true : false;
    }

}
