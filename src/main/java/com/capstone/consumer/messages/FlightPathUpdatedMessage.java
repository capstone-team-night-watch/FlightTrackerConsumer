package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.utils.StringUtils;
import com.capstone.shared.bindings.Airport;
import lombok.Getter;

import java.util.List;

public class FlightPathUpdatedMessage implements SocketMessageInterface {
    @Getter
    private final String flightId;

    @Getter
    private final Airport source;

    @Getter
    private final Airport destination;

    @Getter
    private final List<Double> newCheckPoints;


    public FlightPathUpdatedMessage(FlightInformation flightInformation) {
        this.source = flightInformation.getSource();
        this.flightId = flightInformation.getFlightId();
        this.destination = flightInformation.getDestination();
        this.newCheckPoints = flightInformation.getCheckPoints();
    }

    @Override
    public String getRoom() {
        return "flight-" + this.getFlightId();
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
