package Repositories.AlbumRepository;

import Entities.Album.Album;
import Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
