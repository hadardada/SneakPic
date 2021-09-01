package Controllers.Register;

import UsersManagement.Services.UserService.UserService;
import UsersManagement.UserRegistrationDetails.UserRegistrationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


@Controller
public class Register {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register")
    public String showRegistrationForm(WebRequest request, Model model) {
       // UserRegistrationDetails userData = new UserRegistrationDetails();
       // model.addAttribute("user", userData);
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public void addNewUser(UserRegistrationDetails userRegistrationDetails){
        userService.registerNewUserAccount(userRegistrationDetails);
    }
}
