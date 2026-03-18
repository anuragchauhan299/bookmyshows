package movie.service.bookmyshow.exceptions;

public class SeatsAlreadyBookedException extends Exception{
    public SeatsAlreadyBookedException() {
    }

    public SeatsAlreadyBookedException(String message) {
        super(message);
    }
}
