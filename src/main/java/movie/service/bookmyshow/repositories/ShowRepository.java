package movie.service.bookmyshow.repositories;

import movie.service.bookmyshow.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

    List<Show> findByMovie_IdAndScreen_IdInAndStartTimeBetween(
            Integer movieId,
            List<Integer> screenId,
            LocalDateTime start,
            LocalDateTime end
    );
}
