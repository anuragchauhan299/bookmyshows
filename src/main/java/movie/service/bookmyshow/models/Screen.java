package movie.service.bookmyshow.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class Screen extends BaseModel {
    private String name;
    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "screen-seat")
    private List<Seat> seats;

    @ElementCollection
    @Enumerated(value = EnumType.ORDINAL)
    private List<Features> features;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    @JsonBackReference(value = "theatre-screen")
    private Theatre theatre;
}
