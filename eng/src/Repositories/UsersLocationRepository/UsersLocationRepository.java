package Repositories.UsersLocationRepository;

import Entities.Locations.UsersLocation.UsersLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface UsersLocationRepository extends JpaRepository <UsersLocation, Long> , QuerydslPredicateExecutor{


}
