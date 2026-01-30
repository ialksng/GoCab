package com.gocab.Model;

// make normal app like bala bala
public class Ride {
    // this is major
    public int userId;
    public String source;
    public String destination;
    public int totalSeats;
    public int avaliableSeats;
    public double fare;


    public Ride(int userId, String source, String destination, int totalSeats, int avaliableSeats, double fare) {
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.avaliableSeats = avaliableSeats;
        this.fare = fare;
    }


    public String toString() {
        return "Ride{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", total_seats=" + totalSeats +
                ", avaliable_seats=" + avaliableSeats +
                ", fare=₹" + fare +
        '}';
    }
}
