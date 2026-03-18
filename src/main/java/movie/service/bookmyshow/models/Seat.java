package movie.service.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity
public class Seat extends BaseModel{
    private String name;
    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    private int bottomRightX;
    private int bottomRightY;
    private int topLeftX;
    private int topLeftY;

}
