package movie.service.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Theatre extends BaseModel {
    private String name;
    private String address;
    @OneToMany
    private List<Screen> screens;
}
