package movie.service.bookmyshow.dtos;

import java.util.List;

public class BookTicketRequestDto {
    private List<Integer> showSeatIds;
    private int userId;

    public List<Integer> getShowSeatIds() {
        return showSeatIds;
    }

    public void setShowSeatIds(List<Integer> showSeatIds) {
        this.showSeatIds = showSeatIds;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
