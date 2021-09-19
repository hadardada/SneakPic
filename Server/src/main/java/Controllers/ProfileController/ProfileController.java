package Controllers.ProfileController;

import Entities.User.User;
import Repositories.UserRepository.UserRepository;
import UsersManagement.Services.UserService.SkinnyUser;
import UsersManagement.Services.UserService.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
public class ProfileController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private Gson gson = new Gson();

    @RequestMapping(value = "/user/myProfile")
    public String getProfilePage(){
        return "my-profile";
    }

    @RequestMapping(value = "/user/view-matching-albums")
    public String getMyMatchingAlbumsPage(){
        return "view-albums";
    }

    @RequestMapping(value = "/user/view-my-albums")
    public String getMyAlbumsPage(){
        return "view-albums";
    }

}
