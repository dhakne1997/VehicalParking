package com.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.entity.ParkingSpot;
import com.entity.Vehicle;
import com.service.ParkingService;

import java.util.List;

@Controller
//@RequestMapping("/")
public class ParkingController {

    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping(value = {"/",  "/park/**", "/remove/**", "/spots/**"})
    public String index() {
        return "redirect:/index.html";

    }

    @PostMapping("/api/parking/park")
    @ResponseBody
    public ResponseEntity<ParkingSpot> parkVehicle(@RequestBody Vehicle vehicle) {
        ParkingSpot parkedSpot = parkingService.parkVehicle(vehicle);
        return ResponseEntity.ok(parkedSpot);
    }

    @PostMapping("/api/parking/remove")
    @ResponseBody
    public ResponseEntity<Boolean> removeVehicle(@RequestParam String licensePlate) {
        boolean removed = parkingService.removeVehicle(licensePlate);
        return ResponseEntity.ok(removed);
    }

    @GetMapping("/api/parking/spots")
    @ResponseBody
    public ResponseEntity<List<ParkingSpot>> getAllParkingSpots() {
        List<ParkingSpot> spots = parkingService.getAllParkingSpots();
        return ResponseEntity.ok(spots);
    }
}