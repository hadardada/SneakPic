package Controllers.Home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("/")
    public String home(){
        return ("<h>welcome</h>");
    }

    @GetMapping ("/user")
    public String user(){
        return ("<h>welcome user</h>");
    }

    @GetMapping ("/user/hi")
    public String userHi(){
        return ("<h>hi user</h>");
    }

    @GetMapping ("/admin")
    public String admin(){
        return ("<h>welcome admin</h>");
    }


}
