package movie.service.bookmyshow.repositories;

import movie.service.bookmyshow.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

    @Query("SELECT DISTINCT s.id FROM Show sh JOIN sh.screen s WHERE sh.movie.id = :movieId AND s.id IN :screenIds AND sh.startTime >= :start")
    List<Integer> findScreenIdsForMovieAndScreensOnDate(@Param("movieId") Integer movieId,
                                                        @Param("screenIds") List<Integer> screenIds,
                                                        @Param("start") Date start);

}
