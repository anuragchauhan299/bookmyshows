package movie.service.bookmyshow.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends BaseModel {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "movie_localized_titles", joinColumns = @JoinColumn(name = "movie_id"))
    @MapKeyColumn(name = "locale")
    @Column(name = "localized_title")
    @Builder.Default
    private Map<String, String> localizedTitles = new HashMap<>();
}
