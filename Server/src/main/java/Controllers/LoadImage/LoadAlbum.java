package Controllers.LoadImage;

import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.UserRepository.UserRepository;
import UsersManagement.LoadImageServiece.AlbumDetails;
import UsersManagement.LoadImageServiece.LoadAlbumService;
import UsersManagement.LoadImageServiece.LoadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class LoadAlbum {

    @Autowired
    private LoadAlbumService loadAlbumService;
    private Long currentAlbumId;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    MyImageRepository myImageRepository;

//    @GetMapping(value = "/load-album")
//    public String loadImage(WebRequest request, Model model) {
//        //model.addAttribute("imageService", this.imageDetails);
//        return "loadAlbumForm";
//    }


    @RequestMapping(method = RequestMethod.GET, value = "/user/load-album")
    public String showAlbumForm(){

        //model.addAttribute("imageService", albumDetails);
        return "loadAlbumForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/load-album")
    public String addNewAlbum(@RequestBody AlbumDetails albumDetails) throws IOException {
        this.currentAlbumId = loadAlbumService.addNewAlbum(albumDetails, userRepository, albumRepository);
        return "redirect:/user/load-image";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/load-image")
    public String showImageForm(){
        //model.addAttribute("imageService", albumDetails);
        return "loadImageForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/load-image")
    public String saveImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
       // this.imageDetails = new ImageDetails();
//        String path1 = path.replaceAll("%3A", ":");
//        String path2 = path1.replaceAll("%5C", "/");
//        this.imageDetails.setPath(path2.split("=")[1]);
        LoadImageService.getInstance().saveImage(multipartFile, this.currentAlbumId,
                this.userRepository, this.albumRepository, this.myImageRepository);
        return "loadImageForm";

    }

//    @RequestMapping(method = RequestMethod.POST, value = "/register")
//    public void addNewUser(UserRegistrationDetails userRegistrationDetails){
//        userService.registerNewUserAccount(userRegistrationDetails);
//    }
}
