package movie.service.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ShowSeat extends BaseModel{

    @ManyToOne
    private Show show;
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    private SeatStatus seatStatus;
    @ManyToOne
    private User user;

}
