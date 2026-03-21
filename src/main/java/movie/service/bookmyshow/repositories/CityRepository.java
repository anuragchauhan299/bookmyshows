package movie.service.bookmyshow.repositories;


import movie.service.bookmyshow.models.City;

import java.util.List;
import java.util.Optional;

import movie.service.bookmyshow.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

 

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByNameIgnoreCase(String name);
}
