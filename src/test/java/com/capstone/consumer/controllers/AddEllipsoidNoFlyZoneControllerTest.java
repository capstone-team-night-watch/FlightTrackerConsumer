package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.serviceHandler.AddEllipsoidNoFlyZoneServiceHandler;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddEllipsoidNoFlyZoneControllerTest {

    @Mock
    private AddEllipsoidNoFlyZoneServiceHandler serviceHandler;

    @InjectMocks
    private AddEllipsoidNoFlyZoneController addEllipsoidNoFlyZoneController;

    @Test
    public void shouldCallService(){
        addEllipsoidNoFlyZoneController.addEllipsoidNoFlyZone(new EllipsoidNoFlyZone());

        verify(serviceHandler).handle(any());
    }
}
