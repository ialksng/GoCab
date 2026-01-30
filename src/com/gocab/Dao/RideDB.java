package com.gocab.Dao;

import com.gocab.Model.Ride;
import com.gocab.Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RideDB {

    public void createRide(Ride ride) {
        String sql = "INSERT INTO ride(userId, source, destination, totalSeats, availableSeats, fare) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ride.userId);
            ps.setString(2, ride.source);
            ps.setString(3, ride.destination);
            ps.setInt(4, ride.totalSeats);
            ps.setInt(5, ride.availableSeats);
            ps.setDouble(6, ride.fare);

            ps.executeUpdate();
            System.out.println(" Ride saved to database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAllRides() {
        String sql = "SELECT * FROM ride";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== RIDES IN DATABASE =====");

            boolean found = false;

            while (rs.next()) {
                found = true;

                int id = rs.getInt("rideId");
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                int totalSeats = rs.getInt("totalSeats");
                int availableSeats = rs.getInt("availableSeats");
                double fare = rs.getDouble("fare");
                String rideStatus = rs.getString("rideStatus");

                System.out.println("Ride ID: " + id +
                        " | " + source + " -> " + destination +
                        " | availableSeats -> " + availableSeats +
                        " | total seats -> " + totalSeats +
                        " | Fare: ₹" + fare +
                        " | Ride Status -> " + rideStatus);
            }

            if (!found) {
                System.out.println(" No rides available in database.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAvailableSeats(int rideId, int seatsRequired) {
        boolean exists = false;

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT availableSeats FROM ride WHERE rideId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, rideId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int availableSeats = rs.getInt("availableSeats");

                if (availableSeats >= seatsRequired) {
                    exists = true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error checking seat availability");
            e.printStackTrace();
        }

        return exists;
    }



    public boolean bookSeats(int rideId, int seatsRequired) {

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);  // START TRANSACTION

            // Step 1: Deduct seats safely
            String updateSql = "UPDATE ride SET availableSeats = availableSeats - ? " +
                    "WHERE rideId = ? AND availableSeats >= ?";
            PreparedStatement ps1 = con.prepareStatement(updateSql);
            ps1.setInt(1, seatsRequired);
            ps1.setInt(2, rideId);
            ps1.setInt(3, seatsRequired);

            int rowsUpdated = ps1.executeUpdate();

            if (rowsUpdated == 0) {
                con.rollback();
                return false;
            }

            // Step 2: Mark ride FULL if seats = 0
            String statusSql = "UPDATE ride SET rideStatus = 'FULL' " +
                    "WHERE rideId = ? AND availableSeats = 0";
            PreparedStatement ps2 = con.prepareStatement(statusSql);
            ps2.setInt(1, rideId);
            ps2.executeUpdate();

            con.commit();  // SUCCESS
            return true;

        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
            return false;

        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (Exception ex) {}
        }
    }




}
