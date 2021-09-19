package Controllers.ViewAlbum;

import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.UserRepository.UserRepository;
import UsersManagement.LoadImageServiece.ViewAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewImageController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    MyImageRepository myImageRepository;
    @Autowired
    ViewAlbumService viewAlbumService;

    //returns a status code that tells whether the viewer of the img is the photographer or not
    @RequestMapping(value = "/user/get-photographer/{albumId}/{imgId}")
    public ResponseEntity getPhotographer(@PathVariable Long albumId, @PathVariable Long imgId){
        String viewerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (viewAlbumService.isThePhotographerWatching(albumId, viewerUsername))
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        else
            return new ResponseEntity(HttpStatus.ALREADY_REPORTED);

    }

    @RequestMapping(value = "/user/view-image/{albumId}/{imgId}")
    public String getImagePage() {
        return "view-image";
    }

    @RequestMapping(value = "/user/get-image/{albumId}/{imgId}")
    public @ResponseBody String showImage(@PathVariable String albumId, @PathVariable String imgId) {
        String viewerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Long albumIdLong = Long.parseLong(albumId);
        Long imageIdLong = Long.parseLong(imgId);
        return viewAlbumService.getRelativePath( imageIdLong,albumIdLong,
                viewAlbumService.isThePhotographerWatching(albumIdLong, viewerUsername));

    }

   @RequestMapping(value = "/user/view-image/{dir}/{albumId}/{imgId}")
    public String getPrevOrNextImg(@PathVariable String dir,@PathVariable Long albumId, @PathVariable Long imgId) {
       if (dir.equals("prev")) {
           if (viewAlbumService.isImageFirst(albumId, imgId)) // image is first in album so there's no prev
               return "redirect:/user/view-album/" + albumId.toString();
           else { // returns the img before that one
               return "redirect:/user/view-image/" + albumId.toString() + "/" + viewAlbumService.getPrevImg(albumId, imgId).toString();
           }
       } else { // =="next
           if (viewAlbumService.isImageLast(albumId, imgId))
               return "redirect:/user/view-album/" + albumId.toString();
           else
               return "redirect:/user/view-image/" + albumId.toString() + "/" + viewAlbumService.getNextImg(albumId, imgId).toString();

       }
   }


}
