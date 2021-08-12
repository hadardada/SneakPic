package UsersManagement.Services.MyUserDetailsService;

import Entities.User.User;
import Repositories.UserRepository.UserRepository;
import UsersManagement.MyUserDetails.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user= userRepository.findByUsername(s);
        user.orElseThrow(()-> new UsernameNotFoundException("No Such User: "+ s));
        return user.map(MyUserDetails::new).get();
    }
}
