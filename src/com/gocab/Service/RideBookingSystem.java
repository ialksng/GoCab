package com.gocab.Service;

import com.gocab.Model.BookRide;
import java.util.ArrayList;
import java.util.List;

public class RideBookingSystem {

    public List<BookRide> bookRideList = new ArrayList<>();
    public void bookRide(int rideId, int userId, int seatRequire){
        BookRide bookRide = new BookRide(rideId, userId, seatRequire);

         bookRideList.add(bookRide); // it might not necessary to creat the list
    }
}
