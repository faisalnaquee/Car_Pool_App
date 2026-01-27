public class Booking {
    int booking_id;
    Ride ride;
    User user;
    int seats_booked;
    double total_fare;

    public Booking(Ride ride, int booking_id, User user, int seats_booked, double total_fare) {
        this.ride = ride;
        this.booking_id = booking_id;
        this.user = user;
        this.seats_booked = seats_booked;
        this.total_fare = total_fare;
    }

    @Override
    public String toString() {
        return "Booking ID: " + booking_id +
                "\nUser: " + user.name + " (ID: " + user.user_id + ")" +
                "\nRide Details:\n" + ride +
                "\nSeats Booked: " + seats_booked +
                "\nTotal Fare: $" + total_fare;
    }
}