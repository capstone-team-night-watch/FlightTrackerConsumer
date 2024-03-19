package com.capstone.consumer.messages;

import lombok.Getter;
import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.shared.bindings.FlightInformation;

public class PathCollisionWithNoFlyZoneMessage implements SocketMessageInterface{
    @Getter
    private final BaseNoFlyZone baseNoFlyZone;

    @Getter
    private final FlightInformation flightInformation;


    public PathCollisionWithNoFlyZoneMessage(FlightInformation flightInformation,BaseNoFlyZone baseNoFlyZone)  {
        this.baseNoFlyZone = baseNoFlyZone;
        this.flightInformation = flightInformation;
    }

    @Override
    public String getName() {
        return "path-collision-with-no-fly-zone";
    }

    @Override
    public String getRoom() {
        return this.getFlightInformation().getFlightId();
    }

    @Override
    public String getMessage() {
        return "Flight path has collided with no Fly zone";
    }
}
