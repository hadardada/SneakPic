package Repositories.AlbumRepository;

import Entities.Album.Album;
import Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Iterable<Album> getAlbumsByPhotographer(String username);
    Optional<Album> findById(Long id);
    Optional<Album> getAlbumById(Long id);
    Optional<Album> findFirstByPhotographerOrderByUploadedDateDesc(String photographer);
}
