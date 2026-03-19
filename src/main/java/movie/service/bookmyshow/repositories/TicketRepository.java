package movie.service.bookmyshow.repositories;


import movie.service.bookmyshow.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("SELECT COUNT(t) FROM Ticket t " +
            "WHERE t.user.id = :userId " +
            "AND t.timeOfBooking >= :startDate ")
    long countConfirmedBookingsLast24Hours(@Param("userId") int userId,
                                           @Param("startDate") LocalDateTime startDate);
}
