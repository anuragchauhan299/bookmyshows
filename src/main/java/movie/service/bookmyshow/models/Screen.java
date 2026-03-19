package movie.service.bookmyshow.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Screen extends BaseModel{
    private String name;
    @OneToMany
    private List<Seat> seats;

    @ElementCollection
    @Enumerated(value = EnumType.ORDINAL)
    private List<Features> features;

    @ManyToOne
    private Theatre theatre;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
    public List<Features> getFeatures() { return features; }
    public void setFeatures(List<Features> features) { this.features = features; }
    public Theatre getTheatre() { return theatre; }
    public void setTheatre(Theatre theatre) { this.theatre = theatre; }
}
