package com.gocab.UI;

import com.gocab.Service.CreateRideSystem;
import com.gocab.Service.RideBookingSystem;
import com.gocab.Dao.BookDB;
import com.gocab.Dao.RideDB;
import com.gocab.Dao.UserDB;
import com.gocab.Model.User;
import java.util.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String []args){
        int count =0;
        int b  = 0;
        CreateRideSystem createRideSystem = new CreateRideSystem();
        RideBookingSystem rideBookingSystem = new RideBookingSystem();
        BookDB   bookDB = new BookDB();
        RideDB rideDB = new RideDB();
        UserDB userDB = new UserDB();

        Scanner sc  = new Scanner(System.in);
        
        // User registration/login
        User currentUser = userLoginOrRegister(userDB, sc);
        if (currentUser == null) {
            System.out.println("Failed to login/register. Exiting...");
            return;
        }
        
        System.out.println("Welcome, " + currentUser.name + "!");

        while(true){

            System.out.println("press 1 for create ride");
            System.out.println("press 2 for book ride");
            System.out.println("press 3 for show all rides");
            System.out.println("press any other key for exit\n");



            int option = sc.nextInt();
            // it is use to nextLine and read  enter
            sc.nextLine();
            switch(option){
                case 1:
                        //create ride
                        System.out.println("source ");
                        String source = sc.nextLine();

                        System.out.println("destination");
                        String destination = sc.nextLine();

                        System.out.println("total seat  ");
                        int total_seats = sc.nextInt();

                        System.out.println("seat avaliable");
                        int avaliable_seats = sc.nextInt();
//                        if(!rideDb.isAvaliableSeats(avaliable_seats)){
//                            remove_ride_db(ride_id);
//                        }

                        System.out.println("total fare");
                        int fare = sc.nextInt();

                        createRideSystem.createRide(currentUser.user_id, source,destination,total_seats,avaliable_seats,fare);
                        rideDB.createRide(createRideSystem.rideList.get(count));
                        count+=1;

                        break;
                case 2:
                        //book ride
                    rideDB.showAllRides();
                    System.out.println("select the ride as per  your choice");

                    System.out.println("enter the ride id ");
                    int ride_id = sc.nextInt();

                    if(!bookDB.isRideAvailable(ride_id)){
                        System.out.println("selected ride are not avaliable");
                        break;
                    }

                    System.out.println("enter number of  seat wants to book ");
                    int seats_required = sc.nextInt();

                    if(!rideDB.isAvailableSeats(ride_id , seats_required)){
                        System.out.println("require seats are not avaliable");
                        break;
                    }

                    rideBookingSystem.bookRide(ride_id, currentUser.user_id, seats_required);

                    bookDB.bookRide(rideBookingSystem.bookRideList.get(b));
                    b+=1;

                    rideDB.bookSeats(ride_id,seats_required);




                    break;

                case 3:
                    rideDB.showAllRides();
                    break;
                default:
                    return;
            }
        }

    }

    private static User userLoginOrRegister(UserDB userDB, Scanner sc) {
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    return login(userDB, sc);
                case 2:
                    return register(userDB, sc);
                case 3:
                    return null;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static User login(UserDB userDB, Scanner sc) {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        
        User user = userDB.getUserByEmail(email);
        if (user != null) {
            System.out.println("Login successful!");
            return user;
        } else {
            System.out.println("User not found. Please register first.");
            return null;
        }
    }

    private static User register(UserDB userDB, Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        
        // Check if user already exists
        User existingUser = userDB.getUserByEmail(email);
        if (existingUser != null) {
            System.out.println("User with this email already exists. Please login.");
            return null;
        }
        
        System.out.print("Enter contact number: ");
        long contactNumber = sc.nextLong();
        
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();
        
        // Get current timestamp
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdAt = now.format(formatter);
        
        User newUser = new User(name, email, contactNumber, age, createdAt);
        userDB.addUser(newUser);
        
        System.out.println("Registration successful!");
        
        // Get the user with ID from database
        return userDB.getUserByEmail(email);
    }
}
