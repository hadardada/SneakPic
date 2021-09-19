package Controllers.ViewPurchasedPhotos;

import UsersManagement.LoadImageServiece.ViewAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewPurchasedPhotos {
    @Autowired
    ViewAlbumService viewAlbumService;
    @RequestMapping(value = "/user/get-purchased-images")
    public String getImaes(){
        String viewerUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        //creates and return html page
        return "<html>\n" +
                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"/css/icon-bar.css\">\n" +
                "<link rel=\"stylesheet\"   type=\"text/css\" href=\"/css/notifications.css\">"+
                "<link rel=\"stylesheet\"   type=\"text/css\" href=\"/css/view-album.css\">"+

                "<link rel=\"stylesheet\"   type=\"text/css\" href=\"/css/photographer-bar.css\">"+

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
                "    <a class=\"active\" href=\"myProfile\"> <i class=\"fas fa-user\"></i></a>\n" +
                "    <a href = \"/user/notifications\" class=\"notification\"><i class=\"fas fa-bell\"></i><span class=\"badge\"></span></a>\n" +
                "</div><br>"+
                viewAlbumService.generateImageTags(null,viewerUsername,true) +
                "</body>\n" +"<script src=\"/js/rate.js\"></script>\n"+
                "</html>";
    }

}
