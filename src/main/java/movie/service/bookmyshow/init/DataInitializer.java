package movie.service.bookmyshow.init;

import movie.service.bookmyshow.models.*;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Calendar;


import movie.service.bookmyshow.repositories.CityRepository;
import movie.service.bookmyshow.repositories.TheatreRepository;
import movie.service.bookmyshow.repositories.ScreenRepository;
import movie.service.bookmyshow.repositories.MovieRepository;
import movie.service.bookmyshow.repositories.ShowRepository;
import movie.service.bookmyshow.repositories.UserRepository;
import movie.service.bookmyshow.repositories.TicketRepository;

@Component
@Profile({"dev","seed","local"})
//@DependsOn("flyway")
public class DataInitializer {

  private final CityRepository cityRepository;
  private final TheatreRepository theatreRepository;
  private final ScreenRepository screenRepository;
  private final MovieRepository movieRepository;
  private final ShowRepository showRepository;
  private final UserRepository userRepository;
  private final TicketRepository ticketRepository;

  public DataInitializer(CityRepository cityRepository,
                        TheatreRepository theatreRepository,
                        ScreenRepository screenRepository,
                        MovieRepository movieRepository,
                        ShowRepository showRepository,
                        UserRepository userRepository,
                        TicketRepository ticketRepository) {
    this.cityRepository = cityRepository;
    this.theatreRepository = theatreRepository;
    this.screenRepository = screenRepository;
    this.movieRepository = movieRepository;
    this.showRepository = showRepository;
    this.userRepository = userRepository;
    this.ticketRepository = ticketRepository;
  }

  @PostConstruct
  public void init() {
    seedData();
  }

  @Transactional
  public void seedData() {
    // Idempotent guard: stop if Metro City already exists
    try {
      if (cityRepository.findByNameIgnoreCase("Metro City").isPresent()) {
        return;
      }
    } catch (Exception e) {
      // If the schema isn't ready yet (table missing), skip seeding for now.
      return;
    }

    // 1) City
    City city = new City();
    city.setName("Metro City");
    city = cityRepository.save(city);

    // 2) Theatres in the city
    Theatre t1 = new Theatre();
    t1.setName("Grand Cinema");
    t1 = theatreRepository.save(t1);

    Theatre t2 = new Theatre();
    t2.setName("City Lights");
    t2 = theatreRepository.save(t2);

    // 3) Screens per theatre
    Screen s1 = new Screen();
    s1.setName("Screen 1");
    s1.setTheatre(t1);
    s1 = screenRepository.save(s1);

    Screen s2 = new Screen();
    s2.setName("Screen 2");
    s2.setTheatre(t1);
    s2 = screenRepository.save(s2);

    Screen s3 = new Screen();
    s3.setName("Screen A");
    s3.setTheatre(t2);
    s3 = screenRepository.save(s3);

    // 4) Movie
    Movie m = new Movie();
    m.setName("Sample Movie");
    m.setGenre("Drama");
    m.setTitle("Sample Movie");
    m.setDescription("A dummy movie for seeding");
    m = movieRepository.save(m);

    // 5) Shows
    Date start1;
    Date start2;
    Calendar cal1 = Calendar.getInstance();
    cal1.set(2026, Calendar.MARCH, 19, 10, 0, 0);
    start1 = cal1.getTime();

    Calendar cal2 = Calendar.getInstance();
    cal2.set(2026, Calendar.MARCH, 19, 14, 0, 0);
    start2 = cal2.getTime();

    Show sh1 = new Show();
    sh1.setScreen(s1);
    sh1.setMovie(m);
    sh1.setStartTime(start1);
    showRepository.save(sh1);

    Show sh2 = new Show();
    sh2.setScreen(s3);
    sh2.setMovie(m);
    sh2.setStartTime(start2);
    showRepository.save(sh2);

    // 6) Users
    User u1 = new User();
    u1.setName("Alice");
    u1.setEmail("alice@example.com");
    u1.setPassword("password");
    u1 = userRepository.save(u1);

    User u2 = new User();
    u2.setName("Bob");
    u2.setEmail("bob@example.com");
    u2.setPassword("password");
    u2 = userRepository.save(u2);

    // 7) Tickets
    Ticket tkt1 = new Ticket();
    tkt1.setUser(u1);
    tkt1.setShow(sh1);
    tkt1.setTimeOfBooking(new Date());
    tkt1.setPrice(100.0);
    tkt1.setBookingStatus(BookingStatus.BOOKED);
    tkt1.setPaymentStatus(PaymentStatus.CAPTURED);
    tkt1 = ticketRepository.save(tkt1);

    Ticket tkt2 = new Ticket();
    tkt2.setUser(u2);
    tkt2.setShow(sh2);
    tkt2.setTimeOfBooking(new Date());
    tkt2.setPrice(120.0);
    tkt2.setBookingStatus(BookingStatus.BOOKED);
    tkt2.setPaymentStatus(PaymentStatus.CAPTURED);
    tkt2 = ticketRepository.save(tkt2);
  }

/*  public void clearData() {
    // Clear in appropriate order to satisfy constraints
    ticketRepository.deleteAll();
    showRepository.deleteAll();
    screenRepository.deleteAll();
    theatreRepository.deleteAll();
    cityRepository.deleteAll();
    movieRepository.deleteAll();
    userRepository.deleteAll();
  }*/
}
