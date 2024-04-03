package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.utils.StringUtils;
import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.shared.bindings.FlightInformationKafkaDto;
import lombok.Getter;

public class FlightEnteredNoFlyZoneMessage implements SocketMessageInterface {
    @Getter
    private final BaseNoFlyZone baseNoFlyZone;

    @Getter
    private final FlightInformation flightInformation;

    public FlightEnteredNoFlyZoneMessage(FlightInformation flightInformation, BaseNoFlyZone baseNoFlyZone) {
        this.baseNoFlyZone = baseNoFlyZone;
        this.flightInformation = flightInformation;
    }

    @Override
    public String getName() {
        return StringUtils.getMessageName(FlightEnteredNoFlyZoneMessage.class);
    }

    @Override
    public String getRoom() {
        return "flight-" + this.getFlightInformation().getFlightId();
    }

    @Override
    public String getMessage() {
        return "Flight has entered a no fly zone";
    }
}
