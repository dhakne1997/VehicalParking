package com.dao;


import java.util.List;
import java.util.Optional;

import com.entity.ParkingSpot;
import com.entity.Vehicle;

public interface ParkingDAO {
    ParkingSpot saveParkingSpot(ParkingSpot parkingSpot);
    Vehicle saveVehicle(Vehicle vehicle);
    Optional<ParkingSpot> findAvailableSpot(String vehicleType);
    List<ParkingSpot> getAllParkingSpots();
    Optional<ParkingSpot> findParkingSpotByVehicle(Vehicle vehicle);
    void removeParkingSpot(ParkingSpot parkingSpot);
    Optional<Vehicle> findByLicensePlate(String licensePlate);
}