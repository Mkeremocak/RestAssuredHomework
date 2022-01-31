package config;

public class APIConstans {

    public static class bookingBaseURL{
        public static final String bookingBaseURL="https://restful-booker.herokuapp.com";
    }
    public static class bookingServiceEndpoints{
        public static final String getBooking="/booking";
        public static final String getBookingById="/booking/{id}";
        public static final String createBooking="/booking";
        public static final String updateBooking="/booking/{id}";
        public static final String partialUpdateBooking="/booking/{id}";
        public static final String deleteBooking="/booking/{id}";
        public static final String createToken="/auth";

    }
}
