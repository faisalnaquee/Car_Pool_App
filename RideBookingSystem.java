import java.util.ArrayList;
import java.util.List;

public class RideBookingSystem {
    private List<Ride> rideList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Booking> bookingList = new ArrayList<>();

    
    private int currentUserId = 1;  
    private int currentBookingId = 1;
    private User currentLoggedInUser = null;

    // User Management Methods
    public User signup(String name, String email, String password) {
        // Check if user already exists
        for (User user : userList) {
            if (user.emailId.equals(email)) {
                System.out.println("User with this email already exists!");
                return null;
            }
        }
        
        User newUser = new User(name, currentUserId++, password, email);
        userList.add(newUser);
        System.out.println("Signup successful! User ID: " + newUser.user_id);
        return newUser;
    }

    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null) {
            System.out.println("Email or password cannot be empty");
            return null;
        }
        
        for (User user : userList) {
            if (user.emailId.equals(email) && user.password.equals(password)) {
                currentLoggedInUser = user;
                System.out.println("Login successful! Welcome " + user.name);
                return user;
            }
        }
        
        System.out.println("Invalid email or password");
        return null;
    }

    public void logout() {
        currentLoggedInUser = null;
        System.out.println("Logged out successfully");
    }

    public void updateUserDetails(String name, String email, String password) {
        if (currentLoggedInUser == null) {
            System.out.println("Please login first");
            return;
        }
        
        if (name != null && !name.trim().isEmpty()) {
            currentLoggedInUser.name = name;
        }
        if (email != null && !email.trim().isEmpty()) {
            currentLoggedInUser.emailId = email;
        }
        if (password != null && !password.trim().isEmpty()) {
            currentLoggedInUser.password = password;
        }
        System.out.println("User details updated successfully");
    }

    public void deleteAccount() {
        if (currentLoggedInUser == null) {
            System.out.println("Please login first");
            return;
        }
        
        userList.remove(currentLoggedInUser);
        // Also remove all rides created by this user
        rideList.removeIf(ride -> ride.userId == currentLoggedInUser.user_id);
        currentLoggedInUser = null;
        System.out.println("Account deleted successfully");
    }

    // Ride Management Methods
    public void createRide(String source, String destination, int seats, double fare) {
        if (currentLoggedInUser == null) {
            System.out.println("Please login first to create a ride");
            return;
        }
        
        int rideId = rideList.size() + 1;
        Ride ride = new Ride(rideId, currentLoggedInUser.user_id, seats, source, destination, (long)fare);
        rideList.add(ride);
        System.out.println("Ride Created Successfully:\n" + ride);
    }

    public List<Ride> showAllRides() {
        return new ArrayList<>(rideList); // Return copy to prevent external modification
    }

    public List<Ride> searchRide(String source, String destination, int seats) {
        List<Ride> availableRides = new ArrayList<>();
        for (Ride ride : rideList) {
            if (ride.source.equalsIgnoreCase(source) && 
                ride.destination.equalsIgnoreCase(destination) && 
                ride.numberOfSeats >= seats) {
                availableRides.add(ride);
            }
        }
        return availableRides;
    }

    public void updateRide(int rideId, String source, String destination, int seats, double fare) {
        if (currentLoggedInUser == null) {
            System.out.println("Please login first");
            return;
        }
        
        for (Ride ride : rideList) {
            if (ride.rideId == rideId && ride.userId == currentLoggedInUser.user_id) {
                if (source != null) ride.source = source;
                if (destination != null) ride.destination = destination;
                if (seats > 0) ride.numberOfSeats = seats;
                if (fare > 0) ride.fare = (long)fare;
                System.out.println("Ride updated successfully:\n" + ride);
                return;
            }
        }
        System.out.println("Ride not found or you don't have permission to update it");
    }

    public void deleteRide(int rideId) {
        if (currentLoggedInUser == null) {
            System.out.println("Please login first");
            return;
        }
        
        boolean removed = rideList.removeIf(ride -> 
            ride.rideId == rideId && ride.userId == currentLoggedInUser.user_id
        );
        
        if (removed) {
            System.out.println("Ride deleted successfully");
        } else {
            System.out.println("Ride not found or you don't have permission to delete it");
        }
    }

    // Booking Methods
    public void bookRide(int rideId, int seatsToBook) {
        if (currentLoggedInUser == null) {
            System.out.println("Please login first to book a ride");
            return;
        }
        
        Ride rideToBook = null;
        for (Ride ride : rideList) {
            if (ride.rideId == rideId) {
                rideToBook = ride;
                break;
            }
        }
        
        if (rideToBook == null) {
            System.out.println("Ride not found");
            return;
        }
        
        if (rideToBook.numberOfSeats < seatsToBook) {
            System.out.println("Not enough seats available. Available seats: " + rideToBook.numberOfSeats);
            return;
        }
        
        // Update available seats
        rideToBook.numberOfSeats -= seatsToBook;
        
        // Calculate total fare
        double totalFare = rideToBook.fare * seatsToBook;
        
        // Create booking
        Booking booking = new Booking(rideToBook, currentBookingId++, 
                                    currentLoggedInUser, seatsToBook, totalFare);
        bookingList.add(booking);
        
        System.out.println("Ride booked successfully!\n" + booking);
    }

    public List<Booking> getMyBookings() {
        if (currentLoggedInUser == null) {
            System.out.println("Please login first");
            return new ArrayList<>();
        }
        
        List<Booking> myBookings = new ArrayList<>();
        for (Booking booking : bookingList) {
            if (booking.user.user_id == currentLoggedInUser.user_id) {
                myBookings.add(booking);
            }
        }
        return myBookings;
    }

    public User getCurrentUser() {
        return currentLoggedInUser;
    }
}

//add jar file from mvn repository to module of intellij
//intellij-> open module ->open module settings -> dependency/libraries -> + -> select jar file of java -> add jar file from mvn repository