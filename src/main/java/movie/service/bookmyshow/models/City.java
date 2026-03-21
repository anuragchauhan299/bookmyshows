package movie.service.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class City extends BaseModel {
    private String name;
    @OneToMany
    private List<Theatre> theatres = new ArrayList<>();
    private Boolean active = true;
}
