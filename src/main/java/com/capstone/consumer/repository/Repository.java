package com.capstone.consumer.repository;

import com.capstone.consumer.bindings.*;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
                    SELECT name FROM states_geojson_polygon
                    WHERE ST_Within(ST_MakePoint(:longitude,:latitude), geom)
                """;

        var parameterSource = new MapSqlParameterSource()
                .addValue("longitude", Double.valueOf(longitude))
                .addValue("latitude", Double.valueOf(latitude));

        try {

            var flightLocation = namedParameterJdbcTemplate.queryForObject(
                    query,
                    parameterSource,
                    String.class
            );
            return new GetFlightLocationResponse(flightLocation);

        } catch (IncorrectResultSizeDataAccessException exception) {
            return new GetFlightLocationResponse("Unknown");
        }

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
                    SELECT n.zone_name FROM no_fly_geometry n WHERE
                    ST_Within(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),n.geometry)
                    AND n.max_altitude > :altitude AND n.min_altitude < :altitude
                """;
        var parameterSource = new MapSqlParameterSource()
                .addValue("longitude", longitude)
                .addValue("latitude", latitude)
                .addValue("altitude", altitude);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, parameterSource, String.class);
        } catch (IncorrectResultSizeDataAccessException exception) {
            return "Nothing here";
        }
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
                .addValue("longRadius", noFlyZone.getLongRadius())
                .addValue("latRadius", noFlyZone.getLatRadius())
                .addValue("altRadius", noFlyZone.getAltRadius());

        var query = """
                    INSERT INTO ellipsoid_no_fly_zone
                    (zone_name, longitude, latitude, altitude, long_radius, latitude_radius, altitude_radius)
                    VALUES (:name, :longitude, :latitude, :altitude, :long_radius, :latitude_radius, :altitude_radius)
                """;

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
