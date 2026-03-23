package movie.service.bookmyshow.services;

import movie.service.bookmyshow.dtos.TheatreDto;
import movie.service.bookmyshow.models.*;
import movie.service.bookmyshow.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TheatreIntegrationServiceImpl implements TheatreIntegrationService {
    private final CityRepository cityRepository;
    private final TheatreRepository theatreRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public TheatreIntegrationServiceImpl(CityRepository cityRepository, TheatreRepository theatreRepository, ShowRepository showRepository,
                                         SeatRepository seatRepository, ShowSeatRepository showSeatRepository, ScreenRepository screenRepository, MovieRepository movieRepository) {
        this.cityRepository = cityRepository;
        this.theatreRepository = theatreRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.showSeatRepository = showSeatRepository;
        this.screenRepository = screenRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public City registerTheatre(TheatreDto theatreDto) {
        City city;
        Optional<City> cityOptional = cityRepository.findByNameAndTheatres_NameIn(theatreDto.getCity(), List.of(theatreDto.getName()));
        if (!cityOptional.isPresent()) {
            city = new City();
            city.setName(theatreDto.getCity());
        } else {
            throw new IllegalArgumentException("Theatre already exists in this city");
        }
        // Create theatre without Lombok builder for compatibility
        Theatre theatre = new Theatre();
        theatre.setName(theatreDto.getName());
        theatre.setAddress(theatreDto.getAddress());
        theatre.setType(theatreDto.getType() != null ? theatreDto.getType() : Theatre.TheatreType.MULTIPLEX);
        theatre.setIntegrationType(theatreDto.getIntegrationType() != null ? theatreDto.getIntegrationType() : Theatre.IntegrationType.NEW);
        theatreRepository.save(theatre);
        if (!cityOptional.isPresent()) {
            city.getTheatres().add(theatre);
        }
        return cityRepository.save(city);
    }

    @Override
    @Transactional
    public List<Theatre> initializeSeatInventory(int rows, int col, String cities, String theatreName, int screenCount) {

        Optional<City> cityOptional = cityRepository.findByNameAndTheatres_NameIn(cities, List.of(theatreName));
        City city = cityOptional.get();

        if (cityOptional.isEmpty()) {
            throw new IllegalArgumentException("Theatre not found in this city");
        }
        List<Movie> movieList = new ArrayList<>();
        List<Seat> allSeats = new ArrayList<>();
        List<Screen> screenList = new ArrayList<>();
        List<Show> showList = new ArrayList<>();
        List<ShowSeat> showSeatList = new ArrayList<>();
        List<Theatre> theatreList = cityOptional.get().getTheatres();

        String[] sections = {"PREMIUM", "STANDARD", "STANDARD"};
        BigDecimal[] multipliers = {BigDecimal.valueOf(1.5), BigDecimal.valueOf(1.0), BigDecimal.valueOf(0.8)};
        int count = 0;
        for (int row = 0; row < rows; row++) {
            char rowLetter = (char) ('A' + row);
            for (int seatNum = 1; seatNum <= col; seatNum++) {
                count++;
                Seat seat = Seat.builder()
                        .name("Seat " + rowLetter + " " + seatNum)
                        .seatType(count < count % 3 ? SeatType.GOLD : (count < count % 2 ? SeatType.SILVER : SeatType.PLATINUM))
                        .bottomRightX((row + 1) * seatNum)
                        .bottomRightY((row + 1) * row)
                        .topLeftX((row + 1) * seatNum)
                        .topLeftY((row + 1) * row - 1)
                        .build();
                allSeats.add(seat);
                count++;
            }
        }
        Theatre theatre = theatreList.get(0);
        for (int i = 0; i < screenCount; i++) {
            Screen screen = new Screen();
            screen.setName("Screen " + ('A' + i));
            screen.setSeats(allSeats);
            // Bind screen to its theatre (owning side of the relationship)
            screen.setTheatre(theatre);
            screenRepository.save(screen);
            screenList.add(screen);
            // Do not mutate theatre.getScreens() here to avoid duplicates; ownership is on Screen
        }
        List<Seat> seatList = seatRepository.saveAll(allSeats);
        List<Screen> screenList1 = screenRepository.saveAll(screenList);

        //show
        Show show = new Show();
        Movie movie = new Movie();
        movie.setName("Anurag " + count);
        movie.setTitle("Title " + count);
        movie.setGenre("Genre " + count);
        movie.setDescription("Description " + count);
        movieList.add(movie);
        show.setMovie(movie);
        show.setStartTime(new Date());
        show.setScreen(screenList.get(0));
        showList.add(show);

        //showSeatList
        ShowSeat showSeat = new ShowSeat();
        showSeat.setShow(show);
        showSeat.setSeat(allSeats.get(0));
        showSeat.setSeatStatus(SeatStatus.AVAILABLE);
        showSeatList.add(showSeat);

        List<Movie> movieList1 = movieRepository.saveAll(movieList);

        //List<Screen> screenList1 = screenRepository.saveAll(screenList);
        // Align theatre's screens collection with newly created screens for consistency
        theatre.getScreens().addAll(screenList);

        List<Show> showList1 = showRepository.saveAll(showList);
        List<ShowSeat> showSeatList1 = showSeatRepository.saveAll(showSeatList);
        // If there are multiple theatres, you may want to mirror screens to all; currently ignored to avoid duplicates
        theatreList = theatreRepository.saveAll(theatreList);
        cityRepository.save(city);

        if (seatList.isEmpty() || showList1.isEmpty() || showSeatList1.isEmpty() || movieList1.isEmpty()) {
            throw new IllegalStateException("Failed to initialize seat inventory");
        }

        return theatreList;
    }
}
