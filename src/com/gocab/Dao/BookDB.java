package com.gocab.Dao;

import com.gocab.Model.BookRide;
import com.gocab.Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDB {

    public void bookRide(BookRide bookRide) {

        String sql = "INSERT INTO booking(rideId, userId, seatRequire) " +
             "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,bookRide.rideId);
            ps.setInt(2, bookRide.userId);
            ps.setInt(3, bookRide.seatRequire);

            ps.executeUpdate();
            System.out.println(" Booking saved to database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRideAvailable(int rideId) {
        boolean exists = false;

        try {
            Connection con = DBConnection.getConnection();  // your DB connection
            String query = "SELECT rideId FROM ride WHERE rideId = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, rideId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {   // if row found
                exists = true;
            }

        } catch (Exception e) {
            System.out.println("Error checking ride availability");
            e.printStackTrace();
        }

        return exists;
    }




}
