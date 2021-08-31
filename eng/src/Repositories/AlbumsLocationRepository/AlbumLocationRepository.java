package Repositories.AlbumsLocationRepository;

import Entities.Album.Album;
import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.Locations.UsersLocation.UsersLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface AlbumLocationRepository extends JpaRepository<AlbumsLocation, Long> ,QuerydslPredicateExecutor{

    List<AlbumsLocation> findAllByDate(Date publicationDate);

    List<AlbumsLocation> findAllByTimeBetween(Time TimeStart, Time TimeEnd);

}

