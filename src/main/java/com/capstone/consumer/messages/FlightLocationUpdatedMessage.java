package com.capstone.consumer.messages;


import com.capstone.shared.bindings.FlightInformation;
import lombok.Data;

@Data
public class FlightLocationUpdatedMessage {
    private FlightInformation flightInformation;
}
