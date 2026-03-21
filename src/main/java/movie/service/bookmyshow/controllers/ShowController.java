package movie.service.bookmyshow.controllers;

import movie.service.bookmyshow.models.Theatre;
import movie.service.bookmyshow.services.ShowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    private final ShowService showService;


    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/showTheatres")
    public List<Theatre> showTheater(@RequestParam String cityName, @RequestParam String movieName, @RequestParam Date date) {

        return showService.getTheatresByCity(cityName, date, movieName);
    }
}
