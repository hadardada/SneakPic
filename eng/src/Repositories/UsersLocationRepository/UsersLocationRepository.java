package Repositories.UsersLocationRepository;

import Entities.Locations.UsersLocation.UsersLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface UsersLocationRepository extends JpaRepository <UsersLocation, Long> {

    List<UsersLocation> findAllByDate(Date publicationDate);

    List<UsersLocation> findAllByTimeBetween(Time TimeStart, Time TimeEnd);

    List<UsersLocation> findAllByTimeBetweenAndDate (Time TimeStart, Time TimeEnd, Date publicationDate);
}
