package com.capstone.consumer.repository;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.MilitaryNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private Repository repository;

    private final static PolygonNoFlyZone POLYGON_NO_FLY_ZONE = new PolygonNoFlyZone();

    private final static EllipsoidNoFlyZone ELLIPSOID_NO_FLY_ZONE =
            new EllipsoidNoFlyZone()
                    .setName("NAME")
                    .setLongitude(1)
                    .setLatitude(1)
                    .setAltitude(1)
                    .setLongRadius(1)
                    .setLatRadius(1)
                    .setAltRadius(1);

    private final static MilitaryNoFlyZone MILITARY_NO_FLY_ZONE =
            new MilitaryNoFlyZone("NAME", "GEOJSON");


    @Test
    public void getFlightLocationShouldReturnGetFlightLocationResponse() {
        String longLat = "82.0";

        var flightLocationResponse = repository.getFlightLocation(longLat, longLat);

        assertNotNull(flightLocationResponse);
    }

    @Test
    public void getInNoFlyZoneConflictShouldCallQueryForObject() {
        double longitude = 1.0;
        double latitude= 1.0;
        double altitude = 1.0;
        repository.getInNoFlyZoneConflict(longitude, latitude, altitude);
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(anyString(), any(MapSqlParameterSource.class), eq(String.class));
    }

    @Test
    public void getEllipsoidNoFlyZoneShouldReturnNoFlyZones() {
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(List.of(ELLIPSOID_NO_FLY_ZONE));

        List<EllipsoidNoFlyZone> ellipsoidNoFlyZones = repository.getEllipsoidNoFlyZones();

        assertEquals("NAME", ellipsoidNoFlyZones.get(0).getName());
    }

    @Test
    public void getMilitaryNoFlyZonesShouldReturnNoFlyZones() {
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(List.of(MILITARY_NO_FLY_ZONE));

        List<MilitaryNoFlyZone> militaryNoFlyZones = repository.getMilitaryNoFlyZones();

        assertEquals("NAME", militaryNoFlyZones.get(0).getName());
    }

    @Test
    public void addEllipsoidNoFlyZoneShouldCallAnJdbcUpdate() {
        repository.addEllipsoidNoFlyZone(ELLIPSOID_NO_FLY_ZONE);

        verify(namedParameterJdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    public void deleteNoFlyZoneShouldCall3Templates() {
        repository.deleteNoFlyZone("ZONE");

        verify(namedParameterJdbcTemplate, times(3)).update(anyString(), any(MapSqlParameterSource.class));
    }
}
