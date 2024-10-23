package com.service;

import java.util.List;

import com.entity.ParkingSpot;
import com.entity.Vehicle;

public interface ParkingService {
	ParkingSpot parkVehicle(Vehicle vehicle);

	boolean removeVehicle(String licensePlate);

	List<ParkingSpot> getAllParkingSpots();
}
