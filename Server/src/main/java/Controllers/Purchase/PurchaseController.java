package Controllers.Purchase;

import Entities.Album.Album;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
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
        List<Album> albums = albumRepository.findAll().stream().filter(s->s.getId()%2 ==0).collect(Collectors.toList());
        model.addAttribute(albums);
        //listOfAlbums.addObject(albums);
        return "viewAlbumsToChoose";
    }
}
