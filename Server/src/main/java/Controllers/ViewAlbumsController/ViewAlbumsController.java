package Controllers.ViewAlbumsController;

import UsersManagement.LoadImageServiece.ViewAlbumDetails;
import UsersManagement.LoadImageServiece.ViewAlbumService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ViewAlbumsController {
@Autowired
ViewAlbumService viewAlbumService;

    private Gson gson = new Gson();

    @RequestMapping(value = "/user/view-matching-albums")
    //returns a set of albums that matches the locations of the logged user
    //(this data is already saved in the Notifications table)
    public ResponseEntity getMatchingAlbums(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<ViewAlbumDetails> albums = viewAlbumService.getMatchingAlbums(username);
        if (albums.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(gson.toJson(albums), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/view-my-albums")
    //returns a set of albums that the logged user uploaded
    public ResponseEntity getMyAlbums(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<ViewAlbumDetails> albums = viewAlbumService.getMyAlbums(username);
        if (albums.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(gson.toJson(albums), HttpStatus.OK);
    }
}
