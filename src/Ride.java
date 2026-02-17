public class Ride {
    int id;
    String source;
    String destination;
    int seats;
    double fare;
    User user;

    public Ride(int id, String source, String destination,
                int seats, double fare, User user) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.fare = fare;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ride{id=" + id +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", seats=" + seats +
                ", fare=" + fare +
                '}';
    }
}
