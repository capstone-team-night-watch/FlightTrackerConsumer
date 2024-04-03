package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.enums.Rooms;
import com.capstone.consumer.utils.StringUtils;
import lombok.Getter;

public class FlightCreatedMessage implements SocketMessageInterface {
    @Getter
    public FlightInformation flightInformation;

    public FlightCreatedMessage(FlightInformation flightInformation) {
        this.flightInformation = flightInformation;
    }

    @Override
    public String getRoom() {
        return Rooms.FLIGHT_INFORMATION_LOBBY;
    }

    @Override
    public String getName() {
        return StringUtils.getMessageName(FlightCreatedMessage.class);
    }

    @Override
    public String getMessage() {
        return "A new flight has been created; Access its room to receive live updates!";
    }
}
