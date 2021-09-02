package Controllers.LoadImage;

import Entities.Album.Album;
import Entities.User.User;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.UserRepository.UserRepository;
import UsersManagement.LoadImageServiece.AlbumDetails;
import UsersManagement.LoadImageServiece.LoadAlbumService;
import UsersManagement.LoadImageServiece.LoadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class LoadAlbum {

    @Autowired
    private LoadAlbumService loadAlbumService;
    @Autowired
    private LoadImageService loadImageService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    MyImageRepository myImageRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/user/load-album")
    public String showAlbumForm(){
        return "loadAlbumForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/load-album")
    @ResponseBody
    public void addNewAlbum(@RequestBody AlbumDetails albumDetails) throws IOException {
        Long currentAlbumId = loadAlbumService.addNewAlbum(albumDetails);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/load-image")
    public String showImageForm(){
        return "loadImageForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/load-image")
    public String saveImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        //get username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //get recent album's albumId
        Optional<Album> recentAlbum = albumRepository.findFirstByPhotographerOrderByUploadedDateDesc(username);
        recentAlbum.orElseThrow(()-> new UsernameNotFoundException("No Such Album for user: "+ username));
        loadImageService.saveImage(multipartFile, recentAlbum.get().getId());
        return "loadImageForm";
    }
}
