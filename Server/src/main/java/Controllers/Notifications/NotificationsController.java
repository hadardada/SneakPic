package Controllers.Notifications;

import Repositories.UserRepository.UserRepository;
import NotificationsManagement.NotificationsService.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationsController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationsService notificationsService;

    @RequestMapping( value = "/user/notifications")
    public String getNotifications(){
        //get user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return "<html>\n" +
                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"/css/icon-bar.css\">\n" +
                "<link rel=\"stylesheet\"   type=\"text/css\" href=\"/css/notifications.css\">"+
                "<script src=\"https://kit.fontawesome.com/9a7aeebf11.js\" crossorigin=\"anonymous\"></script>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<meta charset=\"UTF-8\">"+
                "<head>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> </head>\n" +
                "<body>" +
                "<div class=\"icon-bar\">\n" +
                "    <a href=\"/user/home\"> <i class=\"fas fa-home\"></i></a>\n" +
                "    <a href=\"/user/search-location\"><i class=\"fas fa-search-location\"></i></a>\n" +
                "    <a href= \"/user/check-in\"><i class=\"fas fa-map-marker-alt\"></i></a>\n" +
                "    <a href=\"home.html\"> <i class=\"fas fa-user\"></i></a>\n" +
                "    <a class = \"active\" class=\"notification\"><i class=\"fas fa-bell\"></i><span class=\"badge\"></span></a>\n" +
                "</div><br>"+
                "<div class = \"notis\">\n"+
                notificationsService.getNotificationsForUser(username)+"\n </div></body></html>";
    }

}
