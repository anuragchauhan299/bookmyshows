package movie.service.bookmyshow.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatInventoryDto {
    private int rows;
    private int seatsPerRow;
    private String city;
    private String theatreName;
    private int screenCount;
    
    public int getRows() { return rows; }
    public int getSeatsPerRow() { return seatsPerRow; }
    public String getCity() { return city; }
    public String getTheatreName() { return theatreName; }
    public int getScreenCount() { return screenCount; }
}
