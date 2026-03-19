package movie.service.bookmyshow.repositories;


import movie.service.bookmyshow.models.City;
import movie.service.bookmyshow.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<Theatre> findByNameIgnoreCase(@Param("name") String name);
}

