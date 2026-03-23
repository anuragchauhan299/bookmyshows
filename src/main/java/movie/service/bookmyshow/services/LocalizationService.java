package movie.service.bookmyshow.services;

// (no Lombok logging configured in this environment)

import movie.service.bookmyshow.models.Movie;
import movie.service.bookmyshow.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalizationService {
    private final MovieRepository movieRepository;

    public LocalizationService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMoviesNowShowingInCity(String locale) {
        return movieRepository.findByLocalizedTitles(locale);
    }
}
