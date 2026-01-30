package com.gocab.Dao;

import com.gocab.Model.User;
import com.gocab.Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDB {

    public void addUser(User user) {
        String sql = "INSERT INTO user(name, email, contactNumber, age, createdAt) " +
             "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.name);
            ps.setString(2, user.email);
            ps.setLong(3, user.contactNumber);
            ps.setInt(4, user.age);
            ps.setString(5, user.createdAt);

            ps.executeUpdate();
            System.out.println(" User saved to database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getInt("userId"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getLong("contactNumber"),
                    rs.getInt("age"),
                    rs.getString("createdAt")
                );
            }
            
        } catch (Exception e) {
            System.out.println("Error getting user by email");
            e.printStackTrace();
        }
        
        return null;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM user WHERE userId = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getInt("userId"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getLong("contactNumber"),
                    rs.getInt("age"),
                    rs.getString("createdAt")
                );
            }
            
        } catch (Exception e) {
            System.out.println("Error getting user by id");
            e.printStackTrace();
        }
        
        return null;
    }
}
