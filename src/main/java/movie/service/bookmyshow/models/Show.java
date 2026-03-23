package movie.service.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "shows")
public class Show extends BaseModel {

    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Screen screen;
    private Date startTime;
    @ElementCollection
    @Enumerated(value = EnumType.ORDINAL)
    private List<Features> features;
}
