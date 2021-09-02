package Controllers.Home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Home {

    @GetMapping ("/user/home")
    public String userHi(){
        return "home";
    }

    @GetMapping ("/")
    public String afterLogin(){
        return "redirect:/user/home";
    }

//    @GetMapping ("/load-image")
//    public String loadImage(){ return "register"; }

    @GetMapping ("/admin")
    public String admin(){
        return ("<h>welcome admin</h>");
    }


}
