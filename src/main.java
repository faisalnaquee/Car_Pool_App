import java.util.List;

public class main {
    public static void main(String[] args) {

        RideBookingSystem system = new RideBookingSystem();

        User u1 = new User(1, "Aman", "a@mail.com", "123");
        system.signup(u1);

        User loggedIn = system.login("a@mail.com", "123");
        System.out.println("Logged in: " + loggedIn);

        system.createRide(1, "jaipur", "delhi", 5, 300.0, loggedIn);

        List<Ride> rides = system.searchRide("jaipur", "delhi", 2);
        System.out.println(rides);

        system.bookRide(1, 1, loggedIn, 2);
    }
}
