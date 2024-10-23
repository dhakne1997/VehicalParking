package com.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ParkingDAO;
import com.entity.ParkingSpot;
import com.entity.Vehicle;
import com.service.ParkingService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingDAO parkingDAO;

    @Autowired
    public ParkingServiceImpl(ParkingDAO parkingDAO) {
        this.parkingDAO = parkingDAO;
    }

    @Override
    @Transactional
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        Optional<ParkingSpot> availableSpot = parkingDAO.findAvailableSpot(vehicle.getVehicleType());
        if (!availableSpot.isPresent()) {
            vehicle.setSpotType(vehicle.getVehicleType());
            ParkingSpot spot = new ParkingSpot();
            spot.setOccupied(true);
            spot.setVehicle(vehicle);
            spot.setSpotType(vehicle.getVehicleType());
            parkingDAO.saveVehicle(vehicle);
            return parkingDAO.saveParkingSpot(spot);
        }
        throw new RuntimeException("No available parking spot for " + vehicle.getVehicleType());
    }

//    @Override
//    @Transactional
//    public boolean removeVehicle(String licensePlate) {
//        Vehicle vehicle = new Vehicle();
//        vehicle.setLicensePlate(licensePlate);
//        parkingDAO.saveVehicle(vehicle);
//
//        Optional<ParkingSpot> occupiedSpot = parkingDAO.findParkingSpotByVehicle(vehicle);
//        if (occupiedSpot.isPresent()) {
//            parkingDAO.removeParkingSpot(occupiedSpot.get());
////            ParkingSpot spot = occupiedSpot.get();
////            spot.setOccupied(false);
////            spot.setVehicle(null);
////            parkingDAO.saveParkingSpot(spot);
//            return true;
//        }
//        return false;
//    }

    @Override
    @Transactional
    public boolean removeVehicle(String licensePlate) {
        Vehicle vehicle = parkingDAO.findByLicensePlate(licensePlate).get(); // Fetch existing vehicle
        if (vehicle != null) {
            Optional<ParkingSpot> occupiedSpot = parkingDAO.findParkingSpotByVehicle(vehicle);
            if (occupiedSpot.isPresent()) {
                parkingDAO.removeParkingSpot(occupiedSpot.get());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ParkingSpot> getAllParkingSpots() {
        return parkingDAO.getAllParkingSpots();
    }
}
