package Repositories.MyImageRepository;

import Entities.Album.Album;
import Entities.Image.MyImage;
import Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyImageRepository extends JpaRepository<MyImage, String> {
    //Optional<Album> findByName(String username);
    Optional<Album> findById(int id);
}
