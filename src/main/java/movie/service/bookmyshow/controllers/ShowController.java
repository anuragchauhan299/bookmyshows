package movie.service.bookmyshow.controllers;

import movie.service.bookmyshow.dtos.ShowThreaterDto;
import movie.service.bookmyshow.models.Show;
import movie.service.bookmyshow.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/showTheatres")
    public List<Show> showTheater(@RequestBody ShowThreaterDto showThreaterDto) {

        return showService.getTheatresByCity(showThreaterDto.getCityName(), new Date(), showThreaterDto.getMovieName());
    }
}
