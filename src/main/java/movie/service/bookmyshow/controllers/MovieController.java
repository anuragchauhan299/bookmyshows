package movie.service.bookmyshow.controllers;

import movie.service.bookmyshow.models.Movie;
import movie.service.bookmyshow.services.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final LocalizationService localizationService;

    @Autowired
    public MovieController(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @GetMapping
    public List<Movie> getMovies(@RequestParam(defaultValue = "en-US") String locale) {
        return localizationService.getMoviesNowShowingInCity(locale);
    }
}
