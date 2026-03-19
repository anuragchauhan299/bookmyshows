package movie.service.bookmyshow.services;


import movie.service.bookmyshow.exceptions.InvalidUser;
import movie.service.bookmyshow.exceptions.SeatsAlreadyBookedException;
import movie.service.bookmyshow.models.Ticket;

import java.util.List;

public interface TicketService {

    Ticket bookTicket(List<Integer> showSeatIds, int userId) throws SeatsAlreadyBookedException, InvalidUser;

    Ticket confirmBooking(Integer ticketId, String paymentId, String paymentGateway);

    Ticket cancelBooking(int ticketId);
}

