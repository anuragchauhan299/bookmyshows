package movie.service.bookmyshow.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Builder
public class Seat extends BaseModel {
    private String name;
    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    private int bottomRightX;
    private int bottomRightY;
    private int topLeftX;
    private int topLeftY;
    @ManyToOne
    @JoinColumn(name = "screen_id")
    @JsonBackReference(value = "screen-seat")
    private Screen screen;
}
