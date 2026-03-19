package movie.service.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

@Entity
public class SeatTypeShow extends BaseModel{

    @Enumerated(value = EnumType.ORDINAL)
    private SeatType seatType;
    @ManyToOne
    private Show show;
    private double price;

    public SeatType getSeatType() { return seatType; }
    public void setSeatType(SeatType seatType) { this.seatType = seatType; }
    public Show getShow() { return show; }
    public void setShow(Show show) { this.show = show; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
