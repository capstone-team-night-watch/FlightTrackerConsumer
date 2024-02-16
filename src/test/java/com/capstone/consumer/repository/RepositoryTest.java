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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private Repository repository;

    private final static RectangleNoFlyZone RECTANGLE_NO_FLY_ZONE = new RectangleNoFlyZone()
            .setName("NAME")
            .setWestLongDegree(1)
            .setEastLongDegree(1)
            .setSouthLatDegree(1)
            .setNorthLatDegree(1)
            .setRotationDegree(1)
            .setMaxAltitude(1)
            .setMinAltitude(1);


    //    }
    private final static PolygonNoFlyZone POLYGON_NO_FLY_ZONE =
            new PolygonNoFlyZone()
                    .setName("NAME")
                    .setVertex1Long(1)
                    .setVertex1Lat(1)
                    .setVertex2Long(1)
                    .setVertex2Lat(1)
                    .setVertex3Long(1)
                    .setVertex3Lat(1)
                    .setVertex4Long(1)
                    .setVertex4Lat(1)
                    .setMaxAltitude(1)
                    .setMinAltitude(1);

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
    public void getRectangleNoFlyZonesShouldReturnListOfRectangleNoFlyZones() throws SQLException {

        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(Arrays.asList(RECTANGLE_NO_FLY_ZONE));

        List<RectangleNoFlyZone> rectangleNoFlyZoneList = repository.getRectangleNoFlyZones();

        assertEquals("NAME", rectangleNoFlyZoneList.get(0).getName());
    }

    @Test
    public void getPolygonNoFlyZoneShouldReturnNoFlyZones() {
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(List.of(POLYGON_NO_FLY_ZONE));

        List<PolygonNoFlyZone> polygonNoFlyZones = repository.getPolygonNoFlyZones();

        assertEquals("NAME", polygonNoFlyZones.get(0).getName());
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
