package com.capstone.consumer.repository;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.MilitaryNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private Repository repository;

    private final static RectangleNoFlyZone RECTANGLE_NO_FLY_ZONE =
            new RectangleNoFlyZone("NAME", 1, 1, 1, 1,1 ,1, 1);

    private final static PolygonNoFlyZone POLYGON_NO_FLY_ZONE =
            new PolygonNoFlyZone("NAME", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

    private final static EllipsoidNoFlyZone ELLIPSOID_NO_FLY_ZONE =
            new EllipsoidNoFlyZone("NAME", 1, 1, 1, 1, 1, 1);

    private final static MilitaryNoFlyZone MILITARY_NO_FLY_ZONE =
            new MilitaryNoFlyZone("NAME", "GEOJSON");

//    @Test
//    public void getFlightLocationShouldReturnALocation(){
//        when(jdbcTemplate.queryForObject(anyString(), any(), eq(String.class))).thenReturn("Nebraska");
//
//        String location = repository.getFlightLocation("1", "1");
//
//        assertEquals("Nebraska", location);
//    }

    @Test
    public void getRectangleNoFlyZonesShouldReturnListOfRectangleNoFlyZones() throws SQLException {

        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(Arrays.asList(RECTANGLE_NO_FLY_ZONE));

        List<RectangleNoFlyZone> rectangleNoFlyZoneList = repository.getRectangleNoFlyZones();

        assertEquals("NAME", rectangleNoFlyZoneList.get(0).name);
    }

    @Test
    public void getPolygonNoFlyZoneShouldReturnNoFlyZones(){
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(Arrays.asList(POLYGON_NO_FLY_ZONE));

        List<PolygonNoFlyZone> polygonNoFlyZones = repository.getPolygonNoFlyZones();

        assertEquals("NAME", polygonNoFlyZones.get(0).name);
    }

    @Test
    public void getEllipsoidNoFlyZoneShouldReturnNoFlyZones(){
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(Arrays.asList(ELLIPSOID_NO_FLY_ZONE));

        List<EllipsoidNoFlyZone> ellipsoidNoFlyZones = repository.getEllipsoidNoFlyZones();

        assertEquals("NAME", ellipsoidNoFlyZones.get(0).name);
    }

    @Test
    public void getMilitaryNoFlyZonesShouldReturnNoFlyZones(){
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(Arrays.asList(MILITARY_NO_FLY_ZONE));

        List<MilitaryNoFlyZone> militaryNoFlyZones = repository.getMilitaryNoFlyZones();

        assertEquals("NAME", militaryNoFlyZones.get(0).getName());
    }

    @Test
    public void addEllipsoidNoFlyZoneShouldCallAnJdbcUpdate(){
        repository.addEllipsoidNoFlyZone(ELLIPSOID_NO_FLY_ZONE);

        verify(jdbcTemplate).update(anyString(), anyString(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    public void addPolygonNoFlyZoneShouldCallAnJdbcUpdate(){
        repository.addPolygonNoFlyZone(POLYGON_NO_FLY_ZONE);

        verify(jdbcTemplate).update(anyString(), anyString(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    public void addRectangleNoFlyZoneShouldCallAnJdbcUpdate(){
        repository.addRectangleNoFlyZone(RECTANGLE_NO_FLY_ZONE);

        verify(jdbcTemplate).update(anyString(), anyString(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }
}
