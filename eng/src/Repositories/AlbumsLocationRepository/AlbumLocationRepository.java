package Repositories.AlbumsLocationRepository;

import Entities.Album.Album;
import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.Locations.UsersLocation.UsersLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface AlbumLocationRepository extends JpaRepository<AlbumsLocation, Long> {

    List<AlbumsLocation> findAllByDate(Date publicationDate);

    List<AlbumsLocation> findAllByTimeBetween(Time TimeStart, Time TimeEnd);

    @Query (value ="SELECT * FROM AlbumsLocation a WHERE acos(sin(a.latitude * 0.0175) * sin(latX * 0.0175) +\n" +
            "            \"               + cos(a.Latitude * 0.0175) * cos(latX * 0.0175) *   +\n" +
            "            \"                 cos((longY * 0.0175) - (a.Longitude * 0.0175)) +\n" +
            "            \"              ) * 6371  <= 0.1\"",  nativeQuery = true)
    List<AlbumsLocation> findAllWithin100mRadius(
            @Param("latX") float latX, @Param("longY") float longY );
}

