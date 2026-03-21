/*
package movie.service.bookmyshow.setup;

import movie.service.bookmyshow.models.*;
import movie.service.bookmyshow.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private SeatTypeShowRepository seatTypeShowRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        seedAll();
    }

    // Orchestrator
    public void seedAll() {
        if (cityRepository.count() > 0) return;

        City city = seedCity();
        Theatre theatre = seedTheatre();
        city.getTheatres().add(theatre);
        cityRepository.save(city);

        Screen screen = seedScreen(theatre);
        theatre.getScreens().add(screen);
        theatreRepository.save(theatre);

        List<Seat> seats = seedSeats(screen);
        screen.setSeats(seats);
        screenRepository.save(screen);

        Movie movie = seedMovie();
        Show show = seedShow(screen, movie);
        showRepository.save(show);

        User user = seedUser();
        ShowSeat showSeat = seedShowSeat(show, seats.get(0), user);
        showSeatRepository.save(showSeat);

        seedSeatTypeShows(show);
    }

    private City seedCity() {
        City city = new City();
        city.setName("Sample City");
        city.setTheatres(new ArrayList<>());
        return cityRepository.save(city);
    }

    private Theatre seedTheatre() {
        Theatre t = new Theatre();
        t.setName("Grand Cinema");
        t.setAddress("123 Main St");
        t.setScreens(new ArrayList<>());
        return theatreRepository.save(t);
    }

    private Screen seedScreen(Theatre theatre) {
        Screen s = new Screen();
        s.setName("Screen 1");
        s.setTheatre(theatre);
        s.setSeats(new ArrayList<>());
        s.setFeatures(Arrays.asList(Features.TWO_D, Features.IMAX));
        return screenRepository.save(s);
    }

    private List<Seat> seedSeats(Screen screen) {
        Seat s1 = new Seat();
        s1.setName("A1");
        s1.setSeatType(SeatType.GOLD);
        s1.setTopLeftX(0);
        s1.setTopLeftY(0);
        s1.setBottomRightX(100);
        s1.setBottomRightY(90);

        Seat s2 = new Seat();
        s2.setName("A2");
        s2.setSeatType(SeatType.PLATINUM);
        s2.setTopLeftX(10);
        s2.setTopLeftY(0);
        s2.setBottomRightX(110);
        s2.setBottomRightY(90);

        Seat s3 = new Seat();
        s3.setName("A3");
        s3.setSeatType(SeatType.SILVER);
        s3.setTopLeftX(20);
        s3.setTopLeftY(0);
        s3.setBottomRightX(120);
        s3.setBottomRightY(90);

        Seat s4 = new Seat();
        s4.setName("A4");
        s4.setSeatType(SeatType.SILVER);
        s4.setTopLeftX(20);
        s4.setTopLeftY(0);
        s4.setBottomRightX(120);
        s4.setBottomRightY(90);

        Seat s5 = new Seat();
        s5.setName("A5");
        s5.setSeatType(SeatType.PLATINUM);
        s5.setTopLeftX(20);
        s5.setTopLeftY(0);
        s5.setBottomRightX(120);
        s5.setBottomRightY(90);

        Seat s6 = new Seat();
        s6.setName("A6");
        s6.setSeatType(SeatType.GOLD);
        s6.setTopLeftX(20);
        s6.setTopLeftY(0);
        s6.setBottomRightX(120);
        s6.setBottomRightY(90);

        Seat s7 = new Seat();
        s7.setName("A7");
        s7.setSeatType(SeatType.PLATINUM);
        s7.setTopLeftX(20);
        s7.setTopLeftY(0);
        s7.setBottomRightX(120);
        s7.setBottomRightY(90);

        Seat s8 = new Seat();
        s8.setName("A8");
        s8.setSeatType(SeatType.SILVER);
        s8.setTopLeftX(20);
        s8.setTopLeftY(0);
        s8.setBottomRightX(120);
        s8.setBottomRightY(90);

        List<Seat> seats = Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8);
        seatsRepository.saveAll(seats);
        return seats;
    }

    private Movie seedMovie() {
        Movie m = new Movie();
        m.setName("Dummy Movie");
        m.setGenre("Drama");
        return movieRepository.save(m);
    }

    private Show seedShow(Screen screen, Movie movie) {
        Show s = new Show();
        s.setMovie(movie);
        s.setScreen(screen);
        s.setStartTime(new Date());
        s.setFeatures(Arrays.asList(Features.TWO_D, Features.IMAX));
        return showRepository.save(s);
    }

    private User seedUser() {
        User u = new User();
        u.setName("Dummy User");
        u.setEmail("dummy@example.com");
        u.setPassword("password");
        return userRepository.save(u);
    }

    private ShowSeat seedShowSeat(Show show, Seat seat, User user) {
        ShowSeat ss = new ShowSeat();
        ss.setShow(show);
        ss.setSeat(seat);
        ss.setSeatStatus(SeatStatus.AVAILABLE);
        ss.setUser(user);
        return showSeatRepository.save(ss);
    }

    private void seedSeatTypeShows(Show show) {
        SeatTypeShow gold = new SeatTypeShow();
        gold.setSeatType(SeatType.GOLD);
        gold.setShow(show);
        gold.setPrice(12.0);
        seatTypeShowRepository.save(gold);

        SeatTypeShow silver = new SeatTypeShow();
        silver.setSeatType(SeatType.SILVER);
        silver.setShow(show);
        silver.setPrice(9.0);
        seatTypeShowRepository.save(silver);

        SeatTypeShow platinum = new SeatTypeShow();
        platinum.setSeatType(SeatType.PLATINUM);
        platinum.setShow(show);
        platinum.setPrice(18.0);
        seatTypeShowRepository.save(platinum);
    }
}
*/
