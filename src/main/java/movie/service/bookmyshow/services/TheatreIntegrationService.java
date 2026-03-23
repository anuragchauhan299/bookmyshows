package movie.service.bookmyshow.services;

import movie.service.bookmyshow.dtos.TheatreDto;
import movie.service.bookmyshow.models.City;
import movie.service.bookmyshow.models.Theatre;

import java.util.List;

public interface TheatreIntegrationService {
    City registerTheatre(TheatreDto theatreDto);

    List<Theatre> initializeSeatInventory(int rows, int col, String city, String theatreName, int screenCount);
}
