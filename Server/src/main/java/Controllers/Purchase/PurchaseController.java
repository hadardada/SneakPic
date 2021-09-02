package Controllers.Purchase;

import Entities.Album.Album;
import Entities.Image.MyImage;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PurchaseController {

    //@Autowired
   // private PurchaseService purchaseService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    MyImageRepository myImageRepository;


    @RequestMapping(method = RequestMethod.GET, value = "/user/add-purchase")
    public String showRelevantAlbums(Model model){
        //lets say for now, that all relevant albums are even. thaen we collect by position

        //ModelAndView listOfAlbums = new ModelAndView("Albums");
//        List<Album> albums = albumRepository.findAll().stream().filter(s->s.getId()%2 ==0).collect(Collectors.toList());
//        model.addAttribute(albums);
        //listOfAlbums.addObject(albums);
        Long id = Long.getLong("28");
        //Album album28 = albumRepository.getById(id);
        List<MyImage> imagesOfAlbum = myImageRepository.findAll().stream().filter(i->i.getAlbumId() == id).collect(Collectors.toList());

        List<String> imagesMarkedPath = new ArrayList<>();
        imagesOfAlbum.forEach(i -> imagesMarkedPath.add(i.getPathMarked().substring(70)));

        model.addAttribute("images",imagesMarkedPath);

        return "ImagesToView";
    }
}
