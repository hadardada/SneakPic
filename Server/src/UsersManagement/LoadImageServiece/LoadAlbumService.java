package UsersManagement.LoadImageServiece;

import Entities.Album.Album;
import Entities.User.User;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.UserRepository.UserRepository;
import org.apache.tomcat.jni.Time;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class LoadAlbumService {

    private static LoadAlbumService INSTANCE = null;

    public static LoadAlbumService getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new LoadAlbumService();

        return INSTANCE;
    }


    public Long addNewAlbum(AlbumDetails albumDetails, UserRepository userRepository, AlbumRepository albumRepos) {
        Album album = new Album();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user= userRepository.findByUsername(username);
        user.orElseThrow(()-> new UsernameNotFoundException("No Such User: "+ username));

        album.setPhotographer(username);
        Date date = new Date();
        album.setUploadedDate(new Timestamp(date.getTime()));
        album.setName(albumDetails.getName());

        albumRepos.save(album);
        Optional<Album> currentAlbum = albumRepos.findById(album.getId());

        File f = new File("D:\\user\\Desktop\\FinalProgect\\SneakPic\\Server\\src\\main\\resources\\static\\Albums\\" + currentAlbum.get().getId());
        f.mkdirs();
        File fMarked = new File("D:\\user\\Desktop\\FinalProgect\\SneakPic\\Server\\src\\main\\resources\\static\\Albums\\"+currentAlbum.get().getId()+"\\" + "marked");
        fMarked.mkdir();
        return album.getId();
        //System.out.println("");




    }
}
