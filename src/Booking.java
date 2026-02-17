public class Booking {
    int id;
    Ride ride;
    User user;
    int seatsBooked;
    double totalFare;

    public Booking(int id, Ride ride, User user, int seatsBooked) {
        this.id = id;
        this.ride = ride;
        this.user = user;
        this.seatsBooked = seatsBooked;
        this.totalFare = ride.fare * seatsBooked;
    }

    @Override
    public String toString() {
        return "Booking{id=" + id +
                ", rideId=" + ride.id +
                ", userId=" + user.id +
                ", seatsBooked=" + seatsBooked +
                ", totalFare=" + totalFare +
                '}';
    }
}
