package movie.service.bookmyshow.dtos;

import lombok.Getter;
import movie.service.bookmyshow.models.Ticket;

@Getter
public class BookTicketResponseDto {
    private Response response;
    private Ticket ticket;

    public static BookTicketResponseDto getSuccessResponse(Ticket ticket) {
        BookTicketResponseDto dto = new BookTicketResponseDto();
        dto.setResponse(Response.getSuccessResponse("Successfully created ticket"));
        dto.setTicket(ticket);
        return dto;
    }

    public static BookTicketResponseDto getFailureResponse(String message) {
        BookTicketResponseDto dto = new BookTicketResponseDto();
        dto.setResponse(Response.getFailureResponse(message));
        return dto;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
