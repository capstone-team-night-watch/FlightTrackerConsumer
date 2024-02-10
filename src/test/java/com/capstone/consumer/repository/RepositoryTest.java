package com.capstone.consumer.repository;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.MilitaryNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private Repository repository;

    private final static RectangleNoFlyZone RECTANGLE_NO_FLY_ZONE = new RectangleNoFlyZone()
            .name("NAME")
            .westLongDegree(1)
            .eastLongDegree(1)
            .southLatDegree(1)
            .northLatDegree(1)
            .rotationDegree(1)
            .maxAltitude(1)
            .minAltitude(1);


    //    }
    private final static PolygonNoFlyZone POLYGON_NO_FLY_ZONE =
            new PolygonNoFlyZone()
                    .name("NAME")
                    .vertex1Long(1)
                    .vertex1Lat(1)
                    .vertex2Long(1)
                    .vertex2Lat(1)
                    .vertex3Long(1)
                    .vertex3Lat(1)
                    .vertex4Long(1)
                    .vertex4Lat(1)
                    .maxAltitude(1)
                    .minAltitude(1);

    private final static EllipsoidNoFlyZone ELLIPSOID_NO_FLY_ZONE =
            new EllipsoidNoFlyZone()
                    .name("NAME")
                    .longitude(1)
                    .latitude(1)
                    .altitude(1)
                    .longRadius(1)
                    .latRadius(1)
                    .altRadius(1);

    private final static MilitaryNoFlyZone MILITARY_NO_FLY_ZONE =
            new MilitaryNoFlyZone("NAME", "GEOJSON");

    @Test
    public void getRectangleNoFlyZonesShouldReturnListOfRectangleNoFlyZones() throws SQLException {

        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(Arrays.asList(RECTANGLE_NO_FLY_ZONE));

        List<RectangleNoFlyZone> rectangleNoFlyZoneList = repository.getRectangleNoFlyZones();

        assertEquals("NAME", rectangleNoFlyZoneList.get(0).name());
    }

    @Test
    public void getPolygonNoFlyZoneShouldReturnNoFlyZones() {
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(List.of(POLYGON_NO_FLY_ZONE));

        List<PolygonNoFlyZone> polygonNoFlyZones = repository.getPolygonNoFlyZones();

        assertEquals("NAME", polygonNoFlyZones.get(0).name());
    }

    @Test
    public void getEllipsoidNoFlyZoneShouldReturnNoFlyZones() {
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(List.of(ELLIPSOID_NO_FLY_ZONE));

        List<EllipsoidNoFlyZone> ellipsoidNoFlyZones = repository.getEllipsoidNoFlyZones();

        assertEquals("NAME", ellipsoidNoFlyZones.get(0).name());
    }

    @Test
    public void getMilitaryNoFlyZonesShouldReturnNoFlyZones() {
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(List.of(MILITARY_NO_FLY_ZONE));

        List<MilitaryNoFlyZone> militaryNoFlyZones = repository.getMilitaryNoFlyZones();

        assertEquals("NAME", militaryNoFlyZones.get(0).name());
    }

    @Test
    public void addEllipsoidNoFlyZoneShouldCallAnJdbcUpdate() {
        repository.addEllipsoidNoFlyZone(ELLIPSOID_NO_FLY_ZONE);

        verify(namedParameterJdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    public void addPolygonNoFlyZoneShouldCallAnJdbcUpdate() {
        repository.addPolygonNoFlyZone(POLYGON_NO_FLY_ZONE);

        verify(namedParameterJdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    public void addRectangleNoFlyZoneShouldCallAnJdbcUpdate() {
        repository.addRectangleNoFlyZone(RECTANGLE_NO_FLY_ZONE);

        verify(namedParameterJdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }
}
