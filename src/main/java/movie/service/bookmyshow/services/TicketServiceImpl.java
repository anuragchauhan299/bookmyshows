package movie.service.bookmyshow.services;


import movie.service.bookmyshow.constants.AppConstants;
import movie.service.bookmyshow.exceptions.BookingException;
import movie.service.bookmyshow.exceptions.InvalidUser;
import movie.service.bookmyshow.exceptions.SeatsAlreadyBookedException;
import movie.service.bookmyshow.models.*;
import movie.service.bookmyshow.properties.BookingProperties;
import movie.service.bookmyshow.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static movie.service.bookmyshow.models.BookingStatus.BOOKED;
import static movie.service.bookmyshow.models.BookingStatus.CANCELLED;

@Service
public class TicketServiceImpl implements TicketService{

    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;
    private final SeatTypeShowRepository seatTypeShowRepository;
    private final SeatsRepository seatsRepository;
    private final BookingProperties bookingProperties;

    private final TicketRepository ticketRepository;

    private static int count =0;

    @Autowired
    public TicketServiceImpl(ShowSeatRepository showSeatRepository, UserRepository userRepository, SeatTypeShowRepository seatTypeShowRepository, SeatsRepository seatsRepository, BookingProperties bookingProperties, TicketRepository ticketRepository) {
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.seatTypeShowRepository = seatTypeShowRepository;
        this.seatsRepository = seatsRepository;
        this.bookingProperties = bookingProperties;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket bookTicket(List<Integer> showSeatIds, int userId) throws SeatsAlreadyBookedException, InvalidUser {
        count=0;
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){

            throw new InvalidUser("Invalid login");
        }
        //Get User
        User user = userOptional.get();
        validateSeatLimit(showSeatIds);
        // Use the provided userId to validate per-day booking limits
        validateUserBookingLimit(userId);
        List<ShowSeat> showSeats = checkAndBlockSeats(showSeatIds, user);


        //Get show
        Show show = showSeats.get(0).getShow();

        //Get seatIds
        List<Integer> seatIds = showSeats.stream()
                .map( showSeat -> showSeat.getSeat().getId() )
                .toList();
        List<Seat> seats = seatsRepository.findByIdIn(seatIds);


        //Get price
        // Get pricing information from seat type show and for given tickets, determine the correct price
        List<SeatTypeShow> seatTypeShows = seatTypeShowRepository.findByShow_Id(show.getId());

        Map<SeatType, Double> priceMap = new EnumMap<>(SeatType.class);
        for (SeatTypeShow sts: seatTypeShows){
            priceMap.put(sts.getSeatType(), sts.getPrice());
        }

        double totalAmount = 0.0d;
        for (ShowSeat showSeat : showSeats) {
            totalAmount += calculateTotalPrice(priceMap.get(showSeat.getSeat().getSeatType()));
        }

        Ticket ticket = new Ticket();
        ticket.setPrice(totalAmount);
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setSeats(seats);
        ticket.setTimeOfBooking(new Date());
        ticket.setBookingStatus(BookingStatus.BLOCKED);
        return ticketRepository.save(ticket);
    }

    private double calculateTotalPrice(Double totalPrice) {
        int size = ticketRepository.findAll().size();
        double discountPrice = totalPrice;
        if ((size+1)%3==0) {
            discountPrice = discountPrice*50/100;
        } else if (isNowBetweenNoonAndSixExclusive()) {
            discountPrice = discountPrice*20/100;
        }
        return discountPrice;
    }

    public static boolean isNowBetweenNoonAndSixExclusive() {
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        return now.isAfter(LocalTime.NOON) && now.isBefore(LocalTime.of(18, 0));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<ShowSeat> checkAndBlockSeats(List<Integer> showSeatIds, User user) throws SeatsAlreadyBookedException {
        // First lets make sure that the tickets we are trying to book are available
        List<ShowSeat> showSeats = showSeatRepository.findByIdInAndSeatStatus(showSeatIds, SeatStatus.AVAILABLE);

        if(showSeats ==null ||  showSeats.size() != showSeatIds.size()){
            throw new SeatsAlreadyBookedException("The seats you are trying to book is already booked");
        }

        for (ShowSeat showSeat : showSeats) {
            showSeat.setSeatStatus(SeatStatus.BLOCKED);
            showSeat.setUser(user);
            showSeatRepository.save(showSeat);
        }
        return showSeats;
    }

    private void validateSeatLimit(List<Integer> seatNumbers) {
        int maxSeats = bookingProperties.getMaxSeatsPerBooking();
        if (seatNumbers.size() > maxSeats) {
            throw new BookingException(String.format(AppConstants.ErrorMessage.MAX_SEATS_EXCEEDED, maxSeats));
        }
    }

    private void validateUserBookingLimit(int userId) {
        int maxBookings = bookingProperties.getMaxBookingsPerUserPerDay();
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        long todayBookings = ticketRepository.countConfirmedBookingsLast24Hours(userId, startOfDay);
        if (todayBookings >= maxBookings) {
            throw new BookingException(String.format(AppConstants.ErrorMessage.MAX_BOOKINGS_EXCEEDED, maxBookings));
        }
    }
    @Transactional
    public Ticket confirmBooking(Integer ticketId, String paymentId, String paymentGateway) {
        Ticket booking = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new BookingException(AppConstants.ErrorMessage.BOOKING_NOT_FOUND + ticketId));

        if (booking.getBookingStatus() != null && booking.getBookingStatus() != BOOKED) {
            throw new BookingException(AppConstants.ErrorMessage.BOOKING_NOT_PENDING);
        }

        booking.setBookingStatus(BOOKED);
        booking.setId(Integer.parseInt(paymentId));
        booking.setTimeOfBooking(new Date());

        return ticketRepository.save(booking);
    }

    @Transactional
    public Ticket cancelBooking(int ticketId) {
        Ticket booking = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new BookingException(AppConstants.ErrorMessage.BOOKING_NOT_FOUND + ticketId));

        if (booking.getBookingStatus() == CANCELLED) {
            throw new BookingException(AppConstants.ErrorMessage.BOOKING_ALREADY_CANCELLED);
        }

        if (booking.getBookingStatus() == BOOKED) {
            double refundAmount = calculateRefund(booking);
            booking.setPrice(refundAmount);
            booking.setPaymentStatus(PaymentStatus.REFUNDED);
        }

        List<ShowSeat> showSeatList = showSeatRepository.findByShowId(booking.getShow().getId());
        for (ShowSeat showSeat : showSeatList) {
             showSeat.setSeatStatus(SeatStatus.AVAILABLE);
            showSeatRepository.save(showSeat);
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
        return ticketRepository.save(booking);
    }
    private double calculateRefund(Ticket booking) {
        return booking.getPrice();
    }
}
