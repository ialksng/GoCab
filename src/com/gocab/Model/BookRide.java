package com.gocab.Model;

public class BookRide {
   public int rideId;
   public int userId;
   public int seatRequire;

    public BookRide(int rideId, int userId, int seatRequire) {
        this.rideId = rideId;
        this.userId = userId;
        this.seatRequire = seatRequire;
    }
}
