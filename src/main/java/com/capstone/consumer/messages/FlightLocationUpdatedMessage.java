package com.capstone.consumer.messages;

import com.capstone.consumer.utils.StringUtils;
import com.capstone.shared.bindings.GeographicCoordinates3D;
import lombok.Getter;
import com.capstone.consumer.bindings.FlightInformation;

public class FlightLocationUpdatedMessage implements SocketMessageInterface {
    @Getter
    private final String flightId;

    @Getter
    private final GeographicCoordinates3D newLocation;

    public FlightLocationUpdatedMessage(FlightInformation flightInformation) {
        this.flightId = flightInformation.getFlightId();
        this.newLocation = flightInformation.getLocation();
    }

    @Override
    public String getRoom() {
        return "flight-" + this.flightId;
    }

    @Override
    public String getName() {
        return StringUtils.getMessageName(FlightLocationUpdatedMessage.class);
    }

    @Override
    public String getMessage() {
        return "";
    }
}
