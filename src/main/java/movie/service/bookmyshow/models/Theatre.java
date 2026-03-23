package movie.service.bookmyshow.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "theatre")
@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Theatre extends BaseModel {
    private String name;
    private String address;
    @OneToMany(mappedBy = "theatre", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "theatre-screen")
    private List<Screen> screens = new java.util.ArrayList<>();
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "theatre_type", nullable = true)
    private TheatreType type;
    @Column(name = "integration_type", nullable = true)
    @Enumerated(EnumType.ORDINAL)
    private IntegrationType integrationType;

    public enum TheatreType {
        MULTIPLEX, SINGLE_SCREEN, INDOOR, OUTDOOR
    }

    public enum IntegrationType {
        NEW, LEGACY_REST, LEGACY_SOAP, LEGACY_FILE
    }
}
