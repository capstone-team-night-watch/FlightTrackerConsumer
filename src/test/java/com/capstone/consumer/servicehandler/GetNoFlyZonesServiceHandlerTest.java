package com.capstone.consumer.servicehandler;

import com.capstone.consumer.bindings.*;
import com.capstone.consumer.repository.Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetNoFlyZonesServiceHandlerTest {

    @Mock
    private Repository repository;

    @Mock
    private GetNoFlyZonesResponse getNoFlyZonesResponse;

    private GetNoFlyZonesServiceHandler getNoFlyZonesServiceHandler;

    @Before
    public void setUp() {
        getNoFlyZonesServiceHandler = new GetNoFlyZonesServiceHandler(repository);
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
    }

    @Test
    public void handleShouldReturnValid() {
        List<EllipsoidNoFlyZone> ellipsoidNoFlyZoneList = new ArrayList<>();
        ellipsoidNoFlyZoneList.add(new EllipsoidNoFlyZone());
        getNoFlyZonesResponse.setEllipsoidNoFlyZones(ellipsoidNoFlyZoneList);

        List<MilitaryNoFlyZone> militaryNoFlyZoneList = new ArrayList<>();
        militaryNoFlyZoneList.add(new MilitaryNoFlyZone("name", "geoJson"));
        getNoFlyZonesResponse.setMilitaryNoFlyZones(militaryNoFlyZoneList);

        when(repository.getEllipsoidNoFlyZones()).thenReturn(ellipsoidNoFlyZoneList);
        when(repository.getMilitaryNoFlyZones()).thenReturn(militaryNoFlyZoneList);

        GetNoFlyZonesResponse response = getNoFlyZonesServiceHandler.handle();

        assertEquals(getNoFlyZonesResponse, response);
    }

}