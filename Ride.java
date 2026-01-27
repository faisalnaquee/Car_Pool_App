import java.util.ArrayList;

public class Ride {
    int rideId;
    int userId;
    int numberOfSeats;
    String source;
    String destination;
    long fare;
    

    public Ride(int rideId, int userId, int numberOfSeats, String source, String destination, long fare) {
        this.rideId = rideId;
        this.userId = userId;
        this.numberOfSeats = numberOfSeats;
        this.source = source;
        this.destination = destination;
        this.fare = fare;
    }   

    @Override
    public String toString() {
        return "Ride ID : " + rideId + 
                "\nSource : " + source + 
                "\nDestination : " + destination +
                "\nNo of seats : " + numberOfSeats +
                "\nFare : " + fare;
    }

    // Getters and Setters
    public int getRideId() { return rideId; }
    public int getUserId() { return userId; }
    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int seats) { this.numberOfSeats = seats; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public long getFare() { return fare; }
    public void setFare(long fare) { this.fare = fare; }
}