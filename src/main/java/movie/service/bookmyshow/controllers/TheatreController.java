package movie.service.bookmyshow.controllers;


import movie.service.bookmyshow.dtos.TheatreDto;
import movie.service.bookmyshow.models.City;
import movie.service.bookmyshow.services.TheatreIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    private final TheatreIntegrationService theatreIntegrationService;

    @Autowired
    public TheatreController(TheatreIntegrationService theatreIntegrationService) {
        this.theatreIntegrationService = theatreIntegrationService;
    }

    @PostMapping
    public ResponseEntity<City> registerTheatre(@RequestBody TheatreDto dto) {

        City saved = theatreIntegrationService.registerTheatre(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
