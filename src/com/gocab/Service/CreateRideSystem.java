package com.gocab.Service;

import com.gocab.Model.Ride;
import java.util.ArrayList;
import java.util.List;

public class    CreateRideSystem {
    public List<Ride> rideList = new ArrayList<>();
    public void createRide(int userId, String source, String destination, int totalSeats, int availableSeats, double fare){
        Ride ride = new Ride(userId, source,destination, totalSeats,availableSeats,fare);
        rideList.add(ride);
    }
}
