package movie.service.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "city")
@Data
public class City extends BaseModel {
    private String name;
    @OneToMany
    @JoinTable(
        name = "city_theatres",
        joinColumns = @JoinColumn(name = "city_id"),
        inverseJoinColumns = @JoinColumn(name = "theatres_id")
    )
    private List<Theatre> theatres = new ArrayList<>();
    private Boolean active = true;
}
