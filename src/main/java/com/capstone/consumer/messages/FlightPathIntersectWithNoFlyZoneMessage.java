package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.utils.StringUtils;
import lombok.Getter;
import com.capstone.shared.bindings.BaseNoFlyZone;

public class FlightPathIntersectWithNoFlyZoneMessage implements SocketMessageInterface{
    @Getter
    private final BaseNoFlyZone noFlyZone;

    @Getter
    private final FlightInformation flightInformation;


    public FlightPathIntersectWithNoFlyZoneMessage(FlightInformation flightInformation, BaseNoFlyZone baseNoFlyZone)  {
        this.noFlyZone = baseNoFlyZone;
        this.flightInformation = flightInformation;
    }

    @Override
    public String getName() {
        return StringUtils.getMessageName(FlightPathIntersectWithNoFlyZoneMessage.class);
    }

    @Override
    public String getRoom() {
        return "flight-" + this.getFlightInformation().getFlightId();
    }

    @Override
    public String getMessage() {
        return "Flight path has collided with no Fly zone";
    }
}
