package movie.service.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

// Lombok removed to avoid annotation processing in this environment
@Getter
@Entity
public class City extends BaseModel {
    private String name;
    @OneToMany
    private List<Theatre> theatres = new ArrayList<>();
    private Boolean active = true;

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTheatres(List<Theatre> theatres) {
        this.theatres = theatres;
    }
}
