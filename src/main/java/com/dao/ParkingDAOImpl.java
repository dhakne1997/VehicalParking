package com.dao;


import org.springframework.stereotype.Repository;

import com.entity.ParkingSpot;
import com.entity.Vehicle;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;


@Repository
public class ParkingDAOImpl implements ParkingDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ParkingSpot saveParkingSpot(ParkingSpot parkingSpot) {
        entityManager.persist(parkingSpot);
        return parkingSpot;
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        entityManager.persist(vehicle);
        return vehicle;
    }

    @Override
    public Optional<ParkingSpot> findAvailableSpot(String vehicleType) {
        List<ParkingSpot> spots = entityManager.createQuery(
                "SELECT p FROM ParkingSpot p WHERE p.spotType = :type AND p.isOccupied = false", ParkingSpot.class)
                .setParameter("type", vehicleType)
                .setMaxResults(1)
                .getResultList();
        return spots.isEmpty() ? Optional.empty() : Optional.of(spots.get(0));
    }

    @Override
    public List<ParkingSpot> getAllParkingSpots() {
        return entityManager.createQuery("SELECT p FROM ParkingSpot p", ParkingSpot.class).getResultList();
    }

    @Override
    public Optional<ParkingSpot> findParkingSpotByVehicle(Vehicle vehicle) {
        List<ParkingSpot> spots = entityManager.createQuery(
                "SELECT p FROM ParkingSpot p WHERE p.vehicle = :vehicle", ParkingSpot.class)
                .setParameter("vehicle", vehicle)
                .setMaxResults(1)
                .getResultList();
        return spots.isEmpty() ? Optional.empty() : Optional.of(spots.get(0));
    }

    @Override
    public void removeParkingSpot(ParkingSpot parkingSpot) {
        entityManager.remove(parkingSpot);
    }

    @Override
    public Optional<Vehicle> findByLicensePlate(String licensePlate) {
        List<Vehicle> vehicles = entityManager.createQuery(
                        "SELECT v FROM Vehicle v WHERE v.licensePlate = :licensePlate", Vehicle.class)
                .setParameter("licensePlate", licensePlate)
                .getResultList();
        return vehicles.isEmpty() ? Optional.empty() : Optional.of(vehicles.get(0));
    }
}
