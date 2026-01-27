public class Main {
    public static void main(String[] args) {
        RideBookingSystem system = new RideBookingSystem();
        
        // Signup users
        User user1 = system.signup("Adeed", "adeed@gmail.com", "password");
        User user2 = system.signup("John", "john@gmail.com", "pass123");
        
        // Login
        system.login("adeed@gmail.com", "password");
        
        // Create rides
        system.createRide("Tokyo", "California", 5, 50);
        system.createRide("Agra", "Mathura", 3, 30);
        system.createRide("Delhi", "Mumbai", 4, 100);
        
        // Search rides
        System.out.println("\n--- Searching rides from Agra to Mathura ---");
        List<Ride> availableRides = system.searchRide("Agra", "Mathura", 2);
        for (Ride ride : availableRides) {
            System.out.println(ride + "\n");
        }
        
        // Book a ride (login as another user)
        system.logout();
        system.login("john@gmail.com", "pass123");
        system.bookRide(2, 2); // Book 2 seats in ride ID 2
        
        // View bookings
        System.out.println("\n--- My Bookings ---");
        List<Booking> bookings = system.getMyBookings();
        for (Booking booking : bookings) {
            System.out.println(booking + "\n");
        }
    }
}