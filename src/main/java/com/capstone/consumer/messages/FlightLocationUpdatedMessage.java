package com.capstone.consumer.messages;

import com.capstone.consumer.utils.StringUtils;
import lombok.Getter;
import com.capstone.consumer.bindings.FlightInformation;

public class FlightLocationUpdatedMessage implements SocketMessageInterface {
    @Getter
    private final FlightInformation flightInformation;

    public FlightLocationUpdatedMessage(FlightInformation flightInformation) {
        this.flightInformation = new FlightInformation()
                .setLocation(flightInformation.getLocation())
                .setFlightId(flightInformation.getFlightId());
    }

    @Override
    public String getRoom() {
        return "flight-" + this.flightInformation.getFlightId();
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
