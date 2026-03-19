package movie.service.bookmyshow.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.service.bookmyshow.models.Movie;
import movie.service.bookmyshow.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocalizationService {


    private final MovieRepository movieRepository;

    public List<Movie> getMoviesNowShowingInCity(String locale) {

        return movieRepository.findByLocalizedTitles(locale);
    }
}
