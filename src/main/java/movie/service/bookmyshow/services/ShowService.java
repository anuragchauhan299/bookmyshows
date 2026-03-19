package movie.service.bookmyshow.services;

import movie.service.bookmyshow.models.Theatre;

import java.util.Date;
import java.util.List;

public interface ShowService {

    List<Theatre> getTheatresByCity(String city, Date date, String movieName);
}
