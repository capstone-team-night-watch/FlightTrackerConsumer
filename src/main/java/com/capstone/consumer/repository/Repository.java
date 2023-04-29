package com.capstone.consumer.repository;

import com.capstone.consumer.bindings.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class facilitates interaction with the Postgres database set up in AWS.
 */
@Service
public class Repository {
    private static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);

    protected static final String GET_FLIGHT_LOCATION = "SELECT s.\"name\" FROM public.statesgeojson_polygon s WHERE ST_Within(ST_MakePoint(:longitude,:latitude), s.geom)";
    protected static final String FIND_IN_CONFLICT_ZONE = "Select n.zone_name FROM public.noflygeom n WHERE ST_Within(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),n.geometry) AND n.max_altitude > :altitude AND n.min_altitude < :altitude";

    /**
     * Template provided by Spring that allows for interaction with the database without allowing for parameters
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Template provided by Spring that allows for interaction with the database with included parameters
     */
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Determines where a flight is above (Ex. NEBRASKA) given the coordinates
     *
     * @param longitude The longitude value of flight's current location
     * @param latitude  The latitude value of the flight's current location
     * @return The State where a flight is above, or UNKNOWN if the flight's location does not match any in the database
     */
    public GetFlightLocationResponse getFlightLocation(String longitude, String latitude) {
        LOGGER.warn("INSIDE REPO LONG:" + Double.valueOf(longitude));
        LOGGER.warn("REPO LAT:" + Double.valueOf(latitude));
        //This does not work, had to make do with string builder was running into weird errors
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("longitude", Double.valueOf(longitude))
                .addValue("latitude", Double.valueOf(latitude));

        try {
            String flightLocation = namedParameterJdbcTemplate.queryForObject(GET_FLIGHT_LOCATION, parameterSource, String.class);
            return new GetFlightLocationResponse(flightLocation);
        } catch (IncorrectResultSizeDataAccessException e) {
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
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("longitude", longitude)
                .addValue("latitude", latitude)
                .addValue("altitude", altitude);
        try {
            return namedParameterJdbcTemplate.queryForObject(FIND_IN_CONFLICT_ZONE, parameterSource, String.class);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

    }

    /**
     * Get the Rectangle type no-fly zones
     *
     * @return A list of RectangleNoFlyZone Objects if there are values in the db, otherwise an empty list
     */
    public List<RectangleNoFlyZone> getRectangleNoFlyZones() {
        StringBuilder query = new StringBuilder()
                .append(" SELECT * FROM RECTANGLE_NO_FLY_ZONE r");

        return jdbcTemplate.query(query.toString(), (rs, rowNum) -> new RectangleNoFlyZone(
                rs.getString("zone_name"),
                rs.getFloat("WEST_LONG_DEGREE"),
                rs.getFloat("EAST_LONG_DEGREE"),
                rs.getFloat("SOUTH_LAT_DEGREE"),
                rs.getFloat("NORTH_LAT_DEGREE"),
                rs.getFloat("ROTATION_DEGREE"),
                rs.getFloat("MAX_ALTITUDE"),
                rs.getFloat("MIN_ALTITUDE")
        ));
    }

    /**
     * Get the Polygon type no-fly zones
     *
     * @return A list of PolygonNoFlyZone Objects if there are values in the db, otherwise an empty list
     */
    public List<PolygonNoFlyZone> getPolygonNoFlyZones() {
        StringBuilder query = new StringBuilder()
                .append(" SELECT * FROM POLYGON_NO_FLY_ZONE r");

        return jdbcTemplate.query(query.toString(), (rs, rowNum) -> new PolygonNoFlyZone(
                rs.getString("zone_name"),
                rs.getFloat("VERTEX_ONE_LONG"),
                rs.getFloat("VERTEX_ONE_LAT"),
                rs.getFloat("VERTEX_TWO_LONG"),
                rs.getFloat("VERTEX_TWO_LAT"),
                rs.getFloat("VERTEX_THREE_LONG"),
                rs.getFloat("VERTEX_THREE_LAT"),
                rs.getFloat("VERTEX_FOUR_LONG"),
                rs.getFloat("VERTEX_FOUR_LAT"),
                rs.getFloat("MAX_ALTITUDE"),
                rs.getFloat("MIN_ALTITUDE")
        ));
    }

    /**
     * Get the Ellipsoid type no-fly zones
     *
     * @return A list of EllipsoidNoFlyZone Objects if there are values in the db, otherwise an empty list
     */
    public List<EllipsoidNoFlyZone> getEllipsoidNoFlyZones() {
        StringBuilder query = new StringBuilder()
                .append(" SELECT * FROM ELLIPSOID_NO_FLY_ZONE r");

        List<EllipsoidNoFlyZone> ellipsoidNoFlyZones = jdbcTemplate.query(query.toString(), (rs, rowNum) -> new EllipsoidNoFlyZone(
                rs.getString("zone_name"),
                rs.getFloat("longitude"),
                rs.getFloat("latitude"),
                rs.getFloat("altitude"),
                rs.getFloat("longradius"),
                rs.getFloat("latradius"),
                rs.getFloat("altradius")
        ));
        return ellipsoidNoFlyZones;
    }

    /**
     * Get the Military type no-fly zones
     *
     * @return A list of MilitaryNoFlyZone Objects if there are values in the db, otherwise an empty list
     */
    public List<MilitaryNoFlyZone> getMilitaryNoFlyZones() {
        StringBuilder query = new StringBuilder()
                .append(" SELECT * FROM us_military_geojson_8776 r LIMIT 5");

        List<MilitaryNoFlyZone> militaryNoFlyZones = jdbcTemplate.query(query.toString(), (rs, rowNum) -> new MilitaryNoFlyZone(
                String.valueOf(rs.getString("INSTALLATI")),
                rs.getString("geometry")
        ));
        return militaryNoFlyZones;
    }

    /**
     * Adds a new Ellipsoid type no-fly zone
     *
     * @param noFlyZone The EllipsoidNoFlyZone Object that provides the values that will be inserted into the DB
     */
    public void addEllipsoidNoFlyZone(EllipsoidNoFlyZone noFlyZone) {

        jdbcTemplate.update(
                "INSERT INTO ELLIPSOID_NO_FLY_ZONE " +
                        "(ZONE_NAME, LONGITUDE, LATITUDE, ALTITUDE, LONGRADIUS, LATRADIUS, ALTRADIUS)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                noFlyZone.name, noFlyZone.longitude, noFlyZone.latitude, noFlyZone.altitude, noFlyZone.longRadius, noFlyZone.latRadius, noFlyZone.altRadius
        );

    }

    /**
     * Adds a new Polygon type no-fly zone
     *
     * @param noFlyZone The PolygonNoFlyZone Object that provides the values that will be inserted into the DB
     */
    public void addPolygonNoFlyZone(PolygonNoFlyZone noFlyZone) {

        jdbcTemplate.update(
                "INSERT INTO POLYGON_NO_FLY_ZONE " +
                        "(ZONE_NAME, VERTEX_ONE_LONG, VERTEX_ONE_LAT, VERTEX_TWO_LONG, VERTEX_TWO_LAT, VERTEX_THREE_LONG, VERTEX_THREE_LAT, " +
                        "VERTEX_FOUR_LONG, VERTEX_FOUR_LAT, MAX_ALTITUDE, MIN_ALTITUDE)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                noFlyZone.name, noFlyZone.vertex1Long, noFlyZone.vertex1Lat, noFlyZone.vertex2Long, noFlyZone.vertex2Lat, noFlyZone.vertex3Long,
                noFlyZone.vertex3Lat, noFlyZone.vertex4Long, noFlyZone.vertex4Lat, noFlyZone.maxAltitude, noFlyZone.minAltitude
        );

    }

    /**
     * Adds a new Rectangle type no-fly zone
     *
     * @param noFlyZone The RectangleNoFlyZone Object that provides the values that will be inserted into the DB
     */
    public void addRectangleNoFlyZone(RectangleNoFlyZone noFlyZone) {

        jdbcTemplate.update(
                "INSERT INTO RECTANGLE_NO_FLY_ZONE " +
                        "(ZONE_NAME, WEST_LONG_DEGREE, EAST_LONG_DEGREE, SOUTH_LAT_DEGREE, NORTH_LAT_DEGREE, " +
                        "ROTATION_DEGREE, MAX_ALTITUDE, MIN_ALTITUDE)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                noFlyZone.name, noFlyZone.westLongDegree, noFlyZone.eastLongDegree, noFlyZone.southLatDegree, noFlyZone.northLatDegree,
                noFlyZone.rotationDegree, noFlyZone.maxAltitude, noFlyZone.minAltitude
        );

    }
}
