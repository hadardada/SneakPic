package Controllers.ViewAlbum;

import Entities.Image.MyImage;
import Entities.User.User;
import NotificationsManagement.NotificationsService.NotificationsService;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.UserRepository.UserRepository;
import UsersManagement.LoadImageServiece.ViewAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ViewAlbumController {
    @Autowired
    ViewAlbumService viewAlbumService;
    @Autowired
    NotificationsService notificationsService;

    @RequestMapping( value = "/user/view-album/{albumId}")
    public String showAlbum(@PathVariable Long albumId, @RequestHeader("Referer") String referredFrom){
        String viewerUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (referredFrom.contains("notifications")) // if reached that album from notifications -
            // change notification for user regarding this album to be read
            notificationsService.setNotificationToRead(viewerUsername, albumId);

        //creates and return html page
        return "<html>\n" +
             "<link rel=\"stylesheet\"  type=\"text/css\" href=\"/css/icon-bar.css\">\n" +
             "<link rel=\"stylesheet\"   type=\"text/css\" href=\"/css/notifications.css\">"+
                "<link rel=\"stylesheet\"   type=\"text/css\" href=\"/css/view-album.css\">"+

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
             "    <a href = \"/user/notifications\" class=\"notification\"><i class=\"fas fa-bell\"></i><span class=\"badge\"></span></a>\n" +
             "</div><br>"+
        viewAlbumService.generateImageTags(albumId, viewerUsername) +
            "</body>\n" +
            "</html>";
}

}
