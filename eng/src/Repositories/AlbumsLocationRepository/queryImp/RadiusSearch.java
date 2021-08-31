package Repositories.AlbumsLocationRepository.queryImp;

import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.Locations.AlbumsLocation.QAlbumsLocation;
import Entities.Locations.UsersLocation.QUsersLocation;
import Entities.Locations.UsersLocation.UsersLocation;
import Repositories.AlbumsLocationRepository.AlbumLocationRepository;
import Repositories.UsersLocationRepository.UsersLocationRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.acos;
import static com.querydsl.core.types.dsl.MathExpressions.sin;
import static com.querydsl.core.types.dsl.MathExpressions.cos;

public class RadiusSearch {
    private QAlbumsLocation albumsLocation = QAlbumsLocation.albumsLocation;
    private QUsersLocation usersLocation = QUsersLocation.usersLocation;
    @Autowired
    private AlbumLocationRepository albumLocationRepository;
    @Autowired
    private UsersLocationRepository usersLocationRepository;

    /**
     *
     This method should be called when user is uploading his location
     it takes the user location details, and uses it to find matching album/s on a 150 meter radius
     returns a list of AlbumsLocations.
     The Radius search is based on spherical law of cosines, and using the querydsl tool.
     */
    public List<AlbumsLocation> findAllAlbumsByDateTimeAnd150mRadius(Time TimeStart, Time TimeEnd, Date date, float lat, float lon){
        BooleanExpression isDateMatches = albumsLocation.date.eq(date).and(albumsLocation.time.eq(TimeStart)); //TODO address the time range
        BooleanExpression isLocation100m = acos((sin(albumsLocation.latitude.multiply(0.0175)).multiply(Math.sin(lat * 0.0175)))
                        .add(cos(albumsLocation.latitude.multiply(0.0172)).multiply(Math.cos(lat*0.0175))
                                .multiply(cos(albumsLocation.longitude.multiply(0.0175).multiply(-1).add((lon*0.0175)))))).multiply(6371)
                .lt(0.15);
        return (List<AlbumsLocation>) albumLocationRepository.findAll(isDateMatches.and(isLocation100m));
    }


    /**
     *
     This method should be called when an album is uploaded.
     it takes the album location details, and uses it to find matching users locations on a 150 meter radius
     returns a list of UsersLocations.
     The Radius search is based on spherical law of cosines, and using the querydsl tool.
     */
    public List<UsersLocation> findAllUsersByDateTimeAnd150mRadius(Timestamp timeStart, Timestamp timeEnd, float lat, float lon){
        BooleanExpression doesAlbumTimeMatch = usersLocation.from.loe(timeEnd).and(usersLocation.to.goe(timeStart));
        BooleanExpression isAlbumLocation100m = acos((sin(usersLocation.latitude.multiply(0.0175)).multiply(Math.sin(lat * 0.0175)))
                .add(cos(usersLocation.latitude.multiply(0.0172)).multiply(Math.cos(lat*0.0175))
                        .multiply(cos(usersLocation.longitude.multiply(0.0175).multiply(-1).add((lon*0.0175)))))).multiply(6371)
                .lt(0.15);

        return (List<UsersLocation>) usersLocationRepository.findAll(doesAlbumTimeMatch.and(isAlbumLocation100m));

    }

        // acos(sin(a.Latitude * 0.0175) * sin(YOUR_LATITUDE_X * 0.0175)
   //            + cos(a.Latitude * 0.0175) * cos(YOUR_LATITUDE_X * 0.0175) *
  //  cos((YOUR_LONGITUDE_Y * 0.0175) - (a.Longitude * 0.0175))
   //         ) * 3959 <= YOUR_RADIUS_INMILES

}
