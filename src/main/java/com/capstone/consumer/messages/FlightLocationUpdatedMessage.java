package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.bindings.GeographicCoordinates3D;
import lombok.Data;

@Data
public class FlightLocationUpdatedMessage {
    private FlightInformation flightInformation;
}
