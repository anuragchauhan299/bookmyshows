package movie.service.bookmyshow.services;

import movie.service.bookmyshow.models.Show;

import java.util.Date;
import java.util.List;

public interface ShowService {

    List<Show> getTheatresByCity(String city, Date date, String movieName);
}
