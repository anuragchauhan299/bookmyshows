package movie.service.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tickets")
@Data
public class Ticket extends BaseModel {

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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;
}
