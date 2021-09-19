package UsersManagement.Services.UserService;

import Entities.User.User;
import Repositories.UserRepository.UserRepository;
import UsersManagement.UserRegistrationDetails.UserRegistrationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public User registerNewUserAccount(UserRegistrationDetails userData) {
        if (checkIfUserExist(userData.getEmail())) {
            //ToDo EmailExistsException
           // throw new EmailExistsException(
                   // "There is an account with that email address:" + accountDto.getEmail());
        }
        User user = new User();
        user.setFirstName(userData.getFirstName().toLowerCase());
        user.setLastName(userData.getLastName().toLowerCase());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        user.setUsername(userData.getEmail().toLowerCase());
        return userRepository.save(user);
    }
    public boolean checkIfUserExist(String email) {
        return userRepository.findByUsername(email) !=null ? true : false;
    }


    public Set<User> findPhotographersByNameString(String nameString){
        String[] names = nameString.split(" ");
        Set<User> results = new HashSet<>();
        StringBuilder firstName = new StringBuilder();
        StringBuilder lastName = new StringBuilder();
        String firstNameSt;
        String lastNameSt;
        //creates options for first name and last name from the nameString
        //(so if the first name is mentioned last it will still be found)
        for (int i=1; i<names.length;i++){
            for (int j=0; j<i;j++) {
                firstName.append(names[j].toLowerCase() + ' ');
                for (int k =i; k<names.length; k++){
                    lastName.append(names[k].toLowerCase() + ' ');
                }
            }

            userRepository.getAllByFirstNameAndLastName
                    (firstName.substring(0, firstName.length()-1),lastName.substring(0, lastName.length()-1))
                    .forEach((element)->{
                        results.add(element);
                    });

            userRepository.getAllByFirstNameAndLastName
                    (lastName.substring(0, lastName.length()-1),firstName.substring(0, firstName.length()-1))
                    .forEach((element)->{
                        results.add(element);
                    });
            firstName = new StringBuilder();
            lastName = new StringBuilder();
        }
        return results;
    }

    public boolean isUserPhotographer(String user){
        return userRepository.findByUsername(user).get().isPhotographer();
    }
}
