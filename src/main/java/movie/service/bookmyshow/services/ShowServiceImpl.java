package movie.service.bookmyshow.services;


import movie.service.bookmyshow.models.BaseModel;
import movie.service.bookmyshow.models.Movie;
import movie.service.bookmyshow.models.Screen;
import movie.service.bookmyshow.models.Theatre;
import movie.service.bookmyshow.repositories.CityRepository;
import movie.service.bookmyshow.repositories.MovieRepository;
import movie.service.bookmyshow.repositories.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShowServiceImpl implements ShowService {

    private final CityRepository cityRepository;
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;

    public ShowServiceImpl(CityRepository cityRepository, ShowRepository showRepository, MovieRepository movieRepository) {
        this.cityRepository = cityRepository;
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Theatre> getTheatresByCity(String cityName, Date date, String movieName) {

        List<Theatre> theatreList = cityRepository.findByNameIgnoreCase(cityName);

        List<Integer> screenList = theatreList.stream()
                .flatMap(t -> t.getScreens().stream()).map(BaseModel::getId)
                .toList();

        Optional<Movie> movie = movieRepository.findByName(movieName);

        Date dt;
        if(date != null){
            dt = date;
        }else{
            dt = new Date();
        }

        List<Integer> screenId = showRepository.findScreenIdsForMovieAndScreensOnDate(movie.get().getId(),screenList,dt);

        theatreList = theatreList.stream().filter(s-> s.getScreens().stream().anyMatch(ss-> screenId.contains(ss.getId()))).toList();

        return theatreList;
    }
}
