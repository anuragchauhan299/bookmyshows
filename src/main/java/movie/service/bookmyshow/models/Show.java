package movie.service.bookmyshow.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="shows")
public class Show extends BaseModel{

    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Screen screen;
    private Date startTime;
    @ElementCollection
    @Enumerated(value = EnumType.ORDINAL)
    private List<Features> features;

    public int getId() { return this.id; }
    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }
    public Screen getScreen() { return screen; }
    public void setScreen(Screen screen) { this.screen = screen; }
    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public List<Features> getFeatures() { return features; }
    public void setFeatures(List<Features> features) { this.features = features; }
}
