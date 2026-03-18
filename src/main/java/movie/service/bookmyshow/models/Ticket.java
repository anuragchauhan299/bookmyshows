package movie.service.bookmyshow.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Ticket extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "ticket_seats",
        joinColumns = @JoinColumn(name = "ticket_id"),
        inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    private Date timeOfBooking;
    private double price;

    // Lombok-generated setters are not available in this environment; provide explicit setters for compatibility
    public void setPrice(double price) {
        this.price = price;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void setTimeOfBooking(Date timeOfBooking) {
        this.timeOfBooking = timeOfBooking;
    }
}
