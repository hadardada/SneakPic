package Repositories.AlbumRepository;

import Entities.Album.Album;
import Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByName(String username);
    Optional<Album> findById(int id);

}
