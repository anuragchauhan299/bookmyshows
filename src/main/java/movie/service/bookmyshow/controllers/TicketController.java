package movie.service.bookmyshow.controllers;


import movie.service.bookmyshow.dtos.BookTicketRequestDto;
import movie.service.bookmyshow.dtos.BookTicketResponseDto;
import movie.service.bookmyshow.exceptions.BookTicketRequestValidationException;
import movie.service.bookmyshow.exceptions.InvalidUser;
import movie.service.bookmyshow.exceptions.SeatsAlreadyBookedException;
import movie.service.bookmyshow.models.Ticket;
import movie.service.bookmyshow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/bookTicket")
    public BookTicketResponseDto bookTicket(BookTicketRequestDto dto){
        try {
            validateRequest(dto);
            Ticket ticket = ticketService.bookTicket(dto.getShowSeatIds(), dto.getUserId());
            return BookTicketResponseDto.getSuccessResponse(ticket);
        }catch (BookTicketRequestValidationException | SeatsAlreadyBookedException | InvalidUser e){
            return BookTicketResponseDto.getFailureResponse(e.getMessage());
        }catch (Exception e){
            System.out.println("Unhandled exception:" + e.getMessage());
            return BookTicketResponseDto.getFailureResponse(e.getMessage());
        }

    }

    private void validateRequest(BookTicketRequestDto dto) throws BookTicketRequestValidationException{
        if(dto.getUserId() < 0){
            throw  new BookTicketRequestValidationException("Invalid user id");
        }
        if(dto.getShowSeatIds() == null){
            throw new BookTicketRequestValidationException("Seats not selected");
        }

        if(dto.getShowSeatIds().isEmpty()){
            throw new BookTicketRequestValidationException("Atleast 1 seat should be selected");
        }

        if(dto.getShowSeatIds().size() > 10){
            throw new BookTicketRequestValidationException("More than 10 seats can not be selected");
        }

    }

}
