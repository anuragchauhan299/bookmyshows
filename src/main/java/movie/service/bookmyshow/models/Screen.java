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
    @OneToMany
    private List<Seat> seats;

    @ElementCollection
    @Enumerated(value = EnumType.ORDINAL)
    private List<Features> features;

    @ManyToOne
    private Theatre theatre;
}
