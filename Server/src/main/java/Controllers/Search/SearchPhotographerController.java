package Controllers.Search;

import Entities.User.User;
import Repositories.UserRepository.UserRepository;
import UsersManagement.Services.UserService.SkinnyUser;
import UsersManagement.Services.UserService.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class SearchPhotographerController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    private Gson gson = new Gson();

    @RequestMapping(value = "/user/search-photographer")
    public String getFindPhotographerPage(){
        return "find-photographer";
    }

}
