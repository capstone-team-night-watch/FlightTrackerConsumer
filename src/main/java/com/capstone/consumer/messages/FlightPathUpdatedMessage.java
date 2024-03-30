package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.utils.StringUtils;
import lombok.Getter;

public class FlightPathUpdatedMessage implements SocketMessageInterface {
    @Getter
    private final FlightInformation flightInformation;

    public FlightPathUpdatedMessage(FlightInformation flightInformation) {
        this.flightInformation = new FlightInformation()
                .setFlightId(flightInformation.getFlightId())
                .setCheckPoints(flightInformation.getCheckPoints());
    }

    @Override
    public String getRoom() {
        return "flight-" + this.getFlightInformation().getFlightId();
    }

    @Override
    public String getName() {
        return StringUtils.getMessageName(FlightPathUpdatedMessage.class);
    }

    @Override
    public String getMessage() {
        return "";
    }
}
