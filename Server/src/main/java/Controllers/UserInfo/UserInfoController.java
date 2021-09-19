package Controllers.UserInfo;

import Entities.User.User;
import Repositories.UserRepository.UserRepository;
import UsersManagement.Services.UserService.SkinnyUser;
import UsersManagement.Services.UserService.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class UserInfoController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    private Gson gson = new Gson();


    @RequestMapping(value = "/user/is-photographer")
    public ResponseEntity isPhotographer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user= userRepository.findByUsername(username);//since the viewer is
        // a logged user, there's no possibility he's no on th DB
        return new ResponseEntity(gson.toJson(new SkinnyUser(user.get())), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST ,value = "/user/search-photographer/{nameString}")
    public ResponseEntity findPhotographer(@PathVariable String nameString){
        Set<User> results = userService.findPhotographersByNameString(nameString);
        List<SkinnyUser> photogrphrs = new ArrayList<>();
        if (results.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        results.forEach( (element)->{
            photogrphrs.add(new SkinnyUser(element));
        });
        return new ResponseEntity(gson.toJson(photogrphrs), HttpStatus.OK);
    }
}
