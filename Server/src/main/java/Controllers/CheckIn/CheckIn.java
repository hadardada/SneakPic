package Controllers.CheckIn;
import Entities.Locations.UsersLocation.UsersLocation;
import Entities.User.User;
import LocationManagement.Position.Position;
import LocationManagement.services.LocationService.LocationService;
import Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CheckIn {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LocationService locationService;

    @RequestMapping(method = RequestMethod.POST, value = "/check-in")
    public void getUserLocation (Position position){
        //get user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user= userRepository.findByUsername(username);
        user.orElseThrow(()-> new UsernameNotFoundException("No Such User: "+ username));

        //get location + time
        UsersLocation usersLocation = locationService.getUsersLocationObject(position, user.get());

    }
}
