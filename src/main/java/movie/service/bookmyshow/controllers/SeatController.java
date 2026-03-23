package movie.service.bookmyshow.controllers;

import movie.service.bookmyshow.dtos.SeatInventoryDto;
import movie.service.bookmyshow.models.Theatre;
import movie.service.bookmyshow.services.TheatreIntegrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shows/seats")
public class SeatController {

    private final TheatreIntegrationService theatreIntegrationService;

    // Explicit constructor to satisfy environments where Lombok annotation processing isn't active
    public SeatController(TheatreIntegrationService theatreIntegrationService) {
        this.theatreIntegrationService = theatreIntegrationService;
    }

    @PostMapping
    public List<Theatre> initializeSeats(@RequestBody SeatInventoryDto dto) {
        return theatreIntegrationService.initializeSeatInventory(dto.getRows(), dto.getSeatsPerRow(), dto.getCity(), dto.getTheatreName(), dto.getScreenCount());
    }
}
