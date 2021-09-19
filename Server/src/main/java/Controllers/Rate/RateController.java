package Controllers.Rate;

import NotificationsManagement.NotificationsService.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RateController {
    @Autowired
    NotificationsService notificationsService;
    @RequestMapping( method = RequestMethod.POST, value = "/user/rate/{albumId}/{rate}")
    public ResponseEntity castARate(@PathVariable Long albumId, @PathVariable int rate){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (notificationsService.castARate(albumId, username, rate))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.ALREADY_REPORTED);

    }

}
