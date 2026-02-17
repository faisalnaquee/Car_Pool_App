import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RideBookingSystem {

    /* ---------------- USER ---------------- */

    public void signup(User user) {
        String sql =
                "INSERT INTO users (id, name, email, password) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, user.id);
            ps.setString(2, user.name);
            ps.setString(3, user.email);
            ps.setString(4, user.password);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User login(String email, String password) {
        String sql =
                "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* ---------------- RIDE ---------------- */

    public void createRide(int id, String source, String destination,
                           int seats, double fare, User user) {

        String sql =
                "INSERT INTO rides (id, source, destination, seats, fare, user_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, source);
            ps.setString(3, destination);
            ps.setInt(4, seats);
            ps.setDouble(5, fare);
            ps.setInt(6, user.id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Ride> searchRide(String source, String destination, int seats) {

        List<Ride> list = new ArrayList<>();

        String sql =
                "SELECT * FROM rides WHERE source=? AND destination=? AND seats>=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, source);
            ps.setString(2, destination);
            ps.setInt(3, seats);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Ride(
                        rs.getInt("id"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("seats"),
                        rs.getDouble("fare"),
                        null
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ---------------- BOOKING (TRANSACTION) ---------------- */

    public void bookRide(int bookingId, int rideId, User user, int seatsBooked) {

        String getRide =
                "SELECT seats, fare FROM rides WHERE id=?";
        String updateRide =
                "UPDATE rides SET seats = seats - ? WHERE id=?";
        String insertBooking =
                "INSERT INTO bookings (id, ride_id, user_id, seats_booked, total_fare) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(getRide);
            ps1.setInt(1, rideId);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next() || rs.getInt("seats") < seatsBooked) {
                con.rollback();
                System.out.println("Not enough seats");
                return;
            }

            double fare = rs.getDouble("fare");

            PreparedStatement ps2 = con.prepareStatement(updateRide);
            ps2.setInt(1, seatsBooked);
            ps2.setInt(2, rideId);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(insertBooking);
            ps3.setInt(1, bookingId);
            ps3.setInt(2, rideId);
            ps3.setInt(3, user.id);
            ps3.setInt(4, seatsBooked);
            ps3.setDouble(5, fare * seatsBooked);
            ps3.executeUpdate();

            con.commit();
            System.out.println("Booking successful");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
