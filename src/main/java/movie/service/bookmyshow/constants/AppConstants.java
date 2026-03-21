package movie.service.bookmyshow.constants;

public final class AppConstants {

    private AppConstants() {
    }

    public static final class ErrorMessage {
        public static final String BOOKING_NOT_FOUND = "Booking not found: ";
        public static final String BOOKING_NOT_PENDING = "Booking is not in pending state";
        public static final String BOOKING_ALREADY_CANCELLED = "Booking is already cancelled";
        public static final String MAX_SEATS_EXCEEDED = "Maximum %d seats allowed per booking";
        public static final String MAX_BOOKINGS_EXCEEDED = "Maximum %d bookings allowed per day";
    }
    public static final class Payment {
        public static final String STATUS_CAPTURED = "captured";
        public static final String MESSAGE_PAYMENT_SUCCESS = "Payment successful via ";
        public static final String ID_PREFIX_RAZORPAY = "razorpay_";
        public static final String ID_PREFIX_STRIPE = "stripe_";
    }
}
