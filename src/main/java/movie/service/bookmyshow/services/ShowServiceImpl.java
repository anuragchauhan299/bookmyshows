package movie.service.bookmyshow.services;


import movie.service.bookmyshow.models.BaseModel;
import movie.service.bookmyshow.models.City;
import movie.service.bookmyshow.models.Movie;
import movie.service.bookmyshow.models.Show;
import movie.service.bookmyshow.repositories.CityRepository;
import movie.service.bookmyshow.repositories.MovieRepository;
import movie.service.bookmyshow.repositories.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShowServiceImpl implements ShowService {

    private final CityRepository cityRepository;
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ShowServiceImpl(CityRepository cityRepository, ShowRepository showRepository, MovieRepository movieRepository) {
        this.cityRepository = cityRepository;
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Show> getTheatresByCity(String cityName, Date date, String movieName) {
        Optional<City> city = cityRepository.findByName(cityName);
        List<Integer> theatreList = city.get().getTheatres().stream().map(BaseModel::getId)
                .toList();
        Optional<Movie> movie = movieRepository.findByName(movieName);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        return showRepository.findByMovie_IdAndScreen_IdInAndStartTimeBetween(movie.get().getId(), theatreList, start, end);
    }
}
