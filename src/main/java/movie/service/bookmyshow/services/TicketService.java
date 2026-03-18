package movie.service.bookmyshow.services;



import movie.service.bookmyshow.exceptions.InvalidUser;
import movie.service.bookmyshow.exceptions.SeatsAlreadyBookedException;
import movie.service.bookmyshow.models.Ticket;

import java.util.List;

public interface TicketService {

    public Ticket bookTicket(List<Integer> showSeatIds, int userId) throws SeatsAlreadyBookedException, InvalidUser;
}

