package movie.service.bookmyshow.repositories;

import movie.service.bookmyshow.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
}
