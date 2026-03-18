package movie.service.bookmyshow.exceptions;

public class BookTicketRequestValidationException extends Exception{

    public BookTicketRequestValidationException() {
    }

    public BookTicketRequestValidationException(String message) {
        super(message);
    }
}
