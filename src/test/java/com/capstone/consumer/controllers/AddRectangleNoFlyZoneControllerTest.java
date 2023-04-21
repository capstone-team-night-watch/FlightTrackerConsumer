package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.serviceHandler.AddRectangleNoFlyZoneServiceHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddRectangleNoFlyZoneControllerTest {

    @Mock
    private AddRectangleNoFlyZoneServiceHandler serviceHandler;

    @InjectMocks
    private AddRectangleNoFlyZoneController controller;

    @Test
    public void shouldCallService(){
        controller.addRectangleNoFlyZone(new RectangleNoFlyZone());

        verify(serviceHandler).handle(any());
    }

}
