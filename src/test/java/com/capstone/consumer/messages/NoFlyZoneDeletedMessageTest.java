package com.capstone.consumer.messages;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class NoFlyZoneDeletedMessageTest {

    private NoFlyZoneDeletedMessage noFlyZoneDeletedMessage;

    @Before
    public void setUp() {
        noFlyZoneDeletedMessage = new NoFlyZoneDeletedMessage();
    }

    @Test
    public void should_return_no_fly_zone_room() {
        String NO_FLY_ZONE_ROOM = "no-fly-zone-room";
        assertEquals(NO_FLY_ZONE_ROOM, noFlyZoneDeletedMessage.getRoom());
    }

    @Test
    public void should_return_name() {
        String expected = "no-fly-zone-deleted";
        assertEquals(expected, noFlyZoneDeletedMessage.getName());
    }

    @Test
    public void should_return_message() {
        String expected = "No fly zone has been deleted";
        assertEquals(expected, noFlyZoneDeletedMessage.getMessage());
    }

}