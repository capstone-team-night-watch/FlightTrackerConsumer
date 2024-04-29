package com.capstone.shared;

import com.capstone.consumer.entities.FlightInformationEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonHelperTest {

    private FlightInformationEntity flightInformationEntity;

    @Before
    public void setUp() {
        flightInformationEntity = new FlightInformationEntity();
        flightInformationEntity.setId(1L);
        flightInformationEntity.setLocation("location");
        flightInformationEntity.setGroundSpeed(10.00f);
        flightInformationEntity.setHeading(20.00f);
        flightInformationEntity.setSourceAirportName("source-airport-name");
        flightInformationEntity.setDestinationAirportName("destination-airport-name");
    }

    @Test
    public void should_convert_object_to_string() {
        Optional<String> result = JsonHelper.toJson(flightInformationEntity);
        assertTrue(result.isPresent());
    }

    @Test
    public void should_convert_object_to_empty_string() {
        Optional<String> result = JsonHelper.toJson(null);
        assertEquals("null", result.get());
    }

}