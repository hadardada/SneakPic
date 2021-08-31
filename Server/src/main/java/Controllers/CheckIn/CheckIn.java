package Controllers.CheckIn;
import Entities.Locations.UsersLocation.UsersLocation;
import Entities.User.User;
import LocationManagement.Position.Position;
import LocationManagement.LocationService.LocationService;
import Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
public class CheckIn {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LocationService locationService;

    @RequestMapping( value = "/user/check-in")
    public String getCheckinPage () {
        return "check-in";
    }

    @RequestMapping( value = "/user/search-location")
    public String getSerchLocationPage () {
        return "search-location";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/check-in")
    public void getUserLocation (@RequestBody Position position){
        //get user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user= userRepository.findByUsername(username);
        user.orElseThrow(()-> new UsernameNotFoundException("No Such User: "+ username));

        //get location + time
        locationService.uploadUserLocation(position, user.get());
    }
}
