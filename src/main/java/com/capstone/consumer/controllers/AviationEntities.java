package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.bindings.SuccessResponse;
import com.capstone.consumer.servicehandler.LiveTrackingService;
import com.capstone.shared.bindings.BaseNoFlyZone;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AviationEntities {
    public LiveTrackingService liveTrackingService;

    public AviationEntities(LiveTrackingService liveTrackingService) {
        this.liveTrackingService = liveTrackingService;
    }

    @GetMapping("/Flight")
    public SuccessResponse<List<FlightInformation>> getFlightInformation() {
        return new SuccessResponse<>(liveTrackingService.getAllActiveFlight());
    }

    @GetMapping("/NoFlyZone")
    public SuccessResponse<List<BaseNoFlyZone>> getNoFlyZone() {
        return new SuccessResponse<>(liveTrackingService.getAllNoFlyZones());
    }
}
