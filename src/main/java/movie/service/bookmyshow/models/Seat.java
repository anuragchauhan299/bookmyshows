package movie.service.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Seat extends BaseModel {
    private String name;
    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    private int bottomRightX;
    private int bottomRightY;
    private int topLeftX;
    private int topLeftY;
}
