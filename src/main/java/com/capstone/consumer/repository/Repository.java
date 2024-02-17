package com.capstone.consumer.repository;

import com.capstone.consumer.bindings.*;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

/**
 * This class facilitates interaction with the Postgres database set up in AWS.
 */
@Service
public class Repository {
    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Repository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Determines where a flight is above (Ex. NEBRASKA) given the coordinates
     *
     * @param longitude The longitude value of flight's current location
     * @param latitude  The latitude value of the flight's current location
     * @return The State where a flight is above, or Unknown if the flight's location does not match any in the database
     */
    public GetFlightLocationResponse getFlightLocation(String longitude, String latitude) {
        var query = """
                    SELECT name FROM public.statesgeojson_polygon
                    WHERE ST_Within(ST_MakePoint(:longitude,:latitude), geom)
                """;

        var parameterSource = new MapSqlParameterSource()
                .addValue("longitude", Double.valueOf(longitude))
                .addValue("latitude", Double.valueOf(latitude));

        var flightLocation = namedParameterJdbcTemplate.queryForObject(
                query,
                parameterSource,
                String.class
        );

        return new GetFlightLocationResponse(flightLocation);
    }

    /**
     * Determines if a flight is currently in a no-fly zone given the coordinates
     *
     * @param longitude The longitude value of flight's current location
     * @param latitude  The latitude value of the flight's current location
     * @param altitude  The altitude value of the flight's current location
     * @return String value representing the no-fly zone that the flight is currently in contact with
     */
    public String getInNoFlyZoneConflict(Double longitude, Double latitude, Double altitude) {
        var query = """
                    SELECT n.zone_name FROM public.noflygeom n WHERE
                    ST_Within(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),n.geometry)
                    AND n.max_altitude > :altitude AND n.min_altitude < :altitude
                """;
        var parameterSource = new MapSqlParameterSource()
                .addValue("longitude", longitude)
                .addValue("latitude", latitude)
                .addValue("altitude", altitude);

        return namedParameterJdbcTemplate.queryForObject(query, parameterSource, String.class);
    }

    /**
     * Get the Rectangle type no-fly zones
     *
     * @return A list of RectangleNoFlyZone Objects if there are values in the db, otherwise an empty list
     */
    public List<RectangleNoFlyZone> getRectangleNoFlyZones() {
        var query = "SELECT * FROM RECTANGLE_NO_FLY_ZONE r";

        return jdbcTemplate.query(query, (rs, rowNum) -> new RectangleNoFlyZone()
                .setName(rs.getString("zone_name"))
                .setWestLongDegree(rs.getFloat("west_long_degree"))
                .setEastLongDegree(rs.getFloat("east_long_degree"))
                .setSouthLatDegree(rs.getFloat("south_lat_degree"))
                .setNorthLatDegree(rs.getFloat("north_lat_degree"))
                .setRotationDegree(rs.getFloat("rotation_degree"))
                .setMaxAltitude(rs.getFloat("max_altitude"))
                .setMinAltitude(rs.getFloat("min_altitude"))
        );
    }

    /**
     * Get the Polygon type no-fly zones
     *
     * @return A list of PolygonNoFlyZone Objects if there are values in the db, otherwise an empty list
     */
    public List<PolygonNoFlyZone> getPolygonNoFlyZones() {
        return jdbcTemplate.query("SELECT * FROM POLYGON_NO_FLY_ZONE r",
                (rs, rowNum) -> new PolygonNoFlyZone()
                        .setName(rs.getString("zone_name"))
                        .setVertex1Long(rs.getFloat("vertex_one_long"))
                        .setVertex1Lat(rs.getFloat("vertex_one_lat"))
                        .setVertex2Long(rs.getFloat("vertex_two_long"))
                        .setVertex2Lat(rs.getFloat("vertex_two_lat"))
                        .setVertex3Lat(rs.getFloat("vertex_three_long"))
                        .setVertex3Lat(rs.getFloat("vertex_three_lat"))
                        .setVertex4Long(rs.getFloat("vertex_four_long"))
                        .setVertex4Lat(rs.getFloat("vertex_four_lat"))
                        .setMaxAltitude(rs.getFloat("max_altitude"))
                        .setMinAltitude(rs.getFloat("min_altitude"))
        );
    }

    /**
     * Get the Ellipsoid type no-fly zones
     *
     * @return A list of EllipsoidNoFlyZone Objects if there are values in the db, otherwise an empty list
     */
    public List<EllipsoidNoFlyZone> getEllipsoidNoFlyZones() {
        var query = "SELECT * FROM ELLIPSOID_NO_FLY_ZONE r";

        return jdbcTemplate.query(query, (rs, rowNum) -> new EllipsoidNoFlyZone()
                .setName(rs.getString("zone_name"))
                .setLongitude(rs.getFloat("longitude"))
                .setLatitude(rs.getFloat("latitude"))
                .setAltitude(rs.getFloat("altitude"))
                .setLongRadius(rs.getFloat("long_radius"))
                .setLatRadius(rs.getFloat("latitude_radius"))
                .setAltRadius(rs.getFloat("altitude_radius"))
        );
    }

    /**
     * Get the Military type no-fly zones
     *
     * @return A list of MilitaryNoFlyZone Objects if there are values in the db, otherwise an empty list
     */
    public List<MilitaryNoFlyZone> getMilitaryNoFlyZones() {
        var query = "SELECT installation, ST_AsGeoJson(geometry) FROM us_military_geojson";

        return jdbcTemplate.query(query, (rs, rowNum) -> new MilitaryNoFlyZone(
                rs.getString("installation"),
                rs.getString("geometry")
        ));
    }

    /**
     * Adds a new Ellipsoid type no-fly zone
     *
     * @param noFlyZone The EllipsoidNoFlyZone Object that provides the values that will be inserted into the DB
     */
    public void addEllipsoidNoFlyZone(EllipsoidNoFlyZone noFlyZone) {
        var parameterSource = new MapSqlParameterSource()
                .addValue("name", noFlyZone.getName())
                .addValue("longitude", noFlyZone.getLongitude())
                .addValue("latitude", noFlyZone.getLatitude())
                .addValue("altitude", noFlyZone.getAltitude())
                .addValue("long_radius", noFlyZone.getLongRadius())
                .addValue("latitude_radius", noFlyZone.getLatRadius())
                .addValue("altitude_radius", noFlyZone.getAltRadius());

        var query = """
                    INSERT INTO ellipsoid_no_fly_zone
                    (zone_name, longitude, latitude, altitude, long_radius, latitude_radius, altitude_radius)
                    VALUES (:name, :longitude, :latitude, :altitude, :long_radius, :latitude_radius, :altitude_radius)
                """;

        namedParameterJdbcTemplate.update(query, parameterSource);
    }

    /**
     * Adds a new Polygon type no-fly zone
     *
     * @param noFlyZone The PolygonNoFlyZone Object that provides the values that will be inserted into the DB
     */
    public void addPolygonNoFlyZone(PolygonNoFlyZone noFlyZone) {

        var query = """
                    INSERT INTO POLYGON_NO_FLY_ZONE
                    (zone_name, vertex_one_long, vertex_one_lat, vertex_two_long, vertex_two_lat, vertex_three_long, vertex_three_lat, vertex_four_long, vertex_four_lat, max_altitude, min_altitude)
                    VALUES (:name, :vertex1Long, :vertex1Lat, :vertex2Long, :vertex2Lat, :vertex3Long, :vertex3Lat, :vertex4Long, :vertex4Lat, :maxAltitude, :minAltitude)
                """;

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", noFlyZone.getName())
                .addValue("vertex1Long", noFlyZone.getVertex1Long())
                .addValue("vertex1Lat", noFlyZone.getVertex1Lat())
                .addValue("vertex2Long", noFlyZone.getVertex2Long())
                .addValue("vertex2Lat", noFlyZone.getVertex2Lat())
                .addValue("vertex3Long", noFlyZone.getVertex3Long())
                .addValue("vertex3Lat", noFlyZone.getVertex3Lat())
                .addValue("vertex4Long", noFlyZone.getVertex4Long())
                .addValue("vertex4Lat", noFlyZone.getVertex4Lat())
                .addValue("maxAltitude", noFlyZone.getMaxAltitude())
                .addValue("minAltitude", noFlyZone.getMinAltitude());

        namedParameterJdbcTemplate.update(query, parameterSource);
    }

    /**
     * Adds a new Rectangle type no-fly zone
     *
     * @param noFlyZone The RectangleNoFlyZone Object that provides the values that will be inserted into the DB
     */
    public void addRectangleNoFlyZone(RectangleNoFlyZone noFlyZone) {
        var query = """
                    INSERT INTO rectangle_no_fly_zone
                    (zone_name, west_long_degree, east_long_degree, south_lat_degree, north_lat_degree, rotation_degree, max_altitude, min_altitude)
                    VALUES (:name, :westLongDegree, :eastLongDegree, :southLatDegree, :northLatDegree, :rotationDegree, :maxAltitude, :minAltitude)
                """;

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", noFlyZone.getName())
                .addValue("westLongDegree", noFlyZone.getWestLongDegree())
                .addValue("eastLongDegree", noFlyZone.getEastLongDegree())
                .addValue("southLatDegree", noFlyZone.getSouthLatDegree())
                .addValue("northLatDegree", noFlyZone.getNorthLatDegree())
                .addValue("rotationDegree", noFlyZone.getRotationDegree())
                .addValue("maxAltitude", noFlyZone.getMaxAltitude())
                .addValue("minAltitude", noFlyZone.getMinAltitude());

        namedParameterJdbcTemplate.update(query, parameterSource);
    }

    /**
     * Deletes a custom no fly zone
     *
     * @param zoneName The name of the zone to be deleted
     */
    public void deleteNoFlyZone(String zoneName) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("zone_name", zoneName);

        namedParameterJdbcTemplate.update(
                "DELETE FROM ellipsoid_no_fly_zone WHERE zone_name=:zone_name",
                parameterSource
        );

        namedParameterJdbcTemplate.update(
                "DELETE FROM polygon_no_fly_zone WHERE zone_name=:zone_name",
                parameterSource
        );

        namedParameterJdbcTemplate.update(
                "DELETE FROM rectangle_no_fly_zone WHERE zone_name=:zone_name",
                parameterSource
        );
    }
}
