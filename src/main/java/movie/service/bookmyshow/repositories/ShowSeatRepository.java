package movie.service.bookmyshow.repositories;


import jakarta.persistence.LockModeType;
import movie.service.bookmyshow.models.SeatStatus;
import movie.service.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {

    Optional<ShowSeat> findById(int showSeatId);


    @Lock(value = LockModeType.PESSIMISTIC_READ)
    List<ShowSeat> findByIdInAndSeatStatus(List<Integer> showSeatIds, SeatStatus seatStatus);


}
