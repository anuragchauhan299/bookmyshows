package movie.service.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Screen extends BaseModel {
    private String name;
    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    @ElementCollection
    @Enumerated(value = EnumType.ORDINAL)
    private List<Features> features;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;
}
