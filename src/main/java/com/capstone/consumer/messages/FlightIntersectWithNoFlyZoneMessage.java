package com.capstone.consumer.messages;

import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.shared.bindings.FlightInformation;
import lombok.Data;

@Data
public class FlightIntersectWithNoFlyZoneMessage {
    /**
     * True if the flight has already intersected with no-fly-zone false if it is only a prediction
     */
    private boolean hasIntersected;

    private BaseNoFlyZone noFlyZone;

    private FlightInformation flightInformation;

}


