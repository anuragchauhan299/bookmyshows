package movie.service.bookmyshow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import movie.service.bookmyshow.models.BaseModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShowThreaterDto extends BaseModel {
    String cityName;
    String movieName;
    String date;
}
