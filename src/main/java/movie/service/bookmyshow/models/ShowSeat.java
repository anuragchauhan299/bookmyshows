package movie.service.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ShowSeat extends BaseModel {

    @ManyToOne
    private Show show;
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    private SeatStatus seatStatus;
    @ManyToOne
    private User user;
}
