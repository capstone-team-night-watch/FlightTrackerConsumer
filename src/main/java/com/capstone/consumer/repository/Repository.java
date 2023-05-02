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

    // SQL Statements
    // FLIGHT LOCATION RELEVANT SQL Statements
    protected static final String GET_FLIGHT_LOCATION_SQL = "SELECT s.\"name\" FROM public.statesgeojson_polygon s " +
            "WHERE ST_Within(ST_MakePoint(:longitude,:latitude), s.geom)";
    protected static final String FIND_IN_CONFLICT_ZONE_SQL = "SELECT n.zone_name FROM public.noflygeom n WHERE " +
            "ST_Within(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),n.geometry) " +
            "AND n.max_altitude > :altitude AND n.min_altitude < :altitude";

    // GET NO-FLY ZONE Statements
    protected static final String GET_RECTANGLE_NO_FLY_ZONES_SQL = "SELECT * FROM RECTANGLE_NO_FLY_ZONE r";
    protected static final String GET_POLYGON_NO_FLY_ZONES_SQL = "SELECT * FROM POLYGON_NO_FLY_ZONE r";
    protected static final String GET_ELLIPSOID_NO_FLY_ZONES_SQL = "SELECT * FROM ELLIPSOID_NO_FLY_ZONE r";
//    protected static final String GET_MILITARY_NO_FLY_ZONES_SQL = "SELECT * FROM us_military_geojson_8776 r LIMIT 5";
    protected static final String GET_MILITARY_NO_FLY_ZONES_SQL = "SELECT r.\"INSTALLATI\", ST_AsGeoJson(r.geometry) as geometry FROM us_military_geojson_8776 r LIMIT 5";

    // ADD NO-FLY ZONE Statements
    protected static final String ADD_RECTANGLE_NO_FLY_ZONE_SQL = "INSERT INTO RECTANGLE_NO_FLY_ZONE " +
            "(ZONE_NAME, WEST_LONG_DEGREE, EAST_LONG_DEGREE, SOUTH_LAT_DEGREE, NORTH_LAT_DEGREE, " +
            "ROTATION_DEGREE, MAX_ALTITUDE, MIN_ALTITUDE)" +
            "VALUES (:name, :westLongDegree, :eastLongDegree, :southLatDegree, :northLatDegree, :rotationDegree, :maxAltitude, :minAltitude)";

    protected static final String ADD_POLYGON_NO_FLY_ZONE_SQL = "INSERT INTO POLYGON_NO_FLY_ZONE " +
            "(ZONE_NAME, VERTEX_ONE_LONG, VERTEX_ONE_LAT, VERTEX_TWO_LONG, VERTEX_TWO_LAT, VERTEX_THREE_LONG, VERTEX_THREE_LAT, " +
            "VERTEX_FOUR_LONG, VERTEX_FOUR_LAT, MAX_ALTITUDE, MIN_ALTITUDE)" +
            "VALUES (:name, :vertex1Long, :vertex1Lat, :vertex2Long, :vertex2Lat, :vertex3Long, :vertex3Lat, " +
            ":vertex4Long, :vertex4Lat, :maxAltitude, :minAltitude)";

    protected static final String ADD_ELLIPSOID_NO_FLY_ZONE_SQL = "INSERT INTO ELLIPSOID_NO_FLY_ZONE " +
            "(ZONE_NAME, LONGITUDE, LATITUDE, ALTITUDE, LONGRADIUS, LATRADIUS, ALTRADIUS)" +
            "VALUES (:name, :longitude, :latitude, :altitude, :longRadius, :latRadius, :altRadius)";

    // DELETE NO-FLY ZONE SQL Statements
    protected static final String DELETE_POLY_NO_FLY_ZONE = "DELETE FROM public.polygon_no_fly_zone WHERE zone_name=:zoneName";
    protected static final String DELETE_ELLIP_NO_FLY_ZONE = "DELETE FROM public.ellipsoid_no_fly_zone WHERE zone_name=:zoneName";
    protected static final String DELETE_RECTANGLE_NO_FLY_ZONE = "DELETE FROM public.rectangle_no_fly_zone WHERE zone_name=:zoneName";
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
     * @return The State where a flight is above, or Unknown if the flight's location does not match any in the database
     */
    public GetFlightLocationResponse getFlightLocation(String longitude, String latitude) {
        LOGGER.warn("INSIDE REPO LONG:" + Double.valueOf(longitude));
        LOGGER.warn("REPO LAT:" + Double.valueOf(latitude));

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("longitude", Double.valueOf(longitude))
                .addValue("latitude", Double.valueOf(latitude));

        try {
            String flightLocation = namedParameterJdbcTemplate.queryForObject(GET_FLIGHT_LOCATION_SQL, parameterSource, String.class);
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
            return namedParameterJdbcTemplate.queryForObject(FIND_IN_CONFLICT_ZONE_SQL, parameterSource, String.class);
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
        return jdbcTemplate.query(GET_RECTANGLE_NO_FLY_ZONES_SQL, (rs, rowNum) -> new RectangleNoFlyZone(
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
        return jdbcTemplate.query(GET_POLYGON_NO_FLY_ZONES_SQL, (rs, rowNum) -> new PolygonNoFlyZone(
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
        return jdbcTemplate.query(GET_ELLIPSOID_NO_FLY_ZONES_SQL, (rs, rowNum) -> new EllipsoidNoFlyZone(
                rs.getString("zone_name"),
                rs.getFloat("longitude"),
                rs.getFloat("latitude"),
                rs.getFloat("altitude"),
                rs.getFloat("longradius"),
                rs.getFloat("latradius"),
                rs.getFloat("altradius")
        ));
    }

    /**
     * Get the Military type no-fly zones
     *
     * @return A list of MilitaryNoFlyZone Objects if there are values in the db, otherwise an empty list
     */
    public List<MilitaryNoFlyZone> getMilitaryNoFlyZones() {
        return jdbcTemplate.query(GET_MILITARY_NO_FLY_ZONES_SQL, (rs, rowNum) -> new MilitaryNoFlyZone(
                String.valueOf(rs.getString("INSTALLATI")),
                rs.getString("geometry")
        ));
    }

    /**
     * Adds a new Ellipsoid type no-fly zone
     *
     * @param noFlyZone The EllipsoidNoFlyZone Object that provides the values that will be inserted into the DB
     */
    public void addEllipsoidNoFlyZone(EllipsoidNoFlyZone noFlyZone) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", noFlyZone.name)
                .addValue("longitude", noFlyZone.longitude)
                .addValue("latitude", noFlyZone.latitude)
                .addValue("altitude", noFlyZone.altitude)
                .addValue("longRadius", noFlyZone.longRadius)
                .addValue("latRadius", noFlyZone.latRadius)
                .addValue("altRadius", noFlyZone.altRadius);

        namedParameterJdbcTemplate.update(ADD_ELLIPSOID_NO_FLY_ZONE_SQL, parameterSource);
    }

    /**
     * Adds a new Polygon type no-fly zone
     *
     * @param noFlyZone The PolygonNoFlyZone Object that provides the values that will be inserted into the DB
     */
    public void addPolygonNoFlyZone(PolygonNoFlyZone noFlyZone) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", noFlyZone.name)
                .addValue("vertex1Long", noFlyZone.vertex1Long)
                .addValue("vertex1Lat", noFlyZone.vertex1Lat)
                .addValue("vertex2Long", noFlyZone.vertex2Long)
                .addValue("vertex2Lat", noFlyZone.vertex2Lat)
                .addValue("vertex3Long", noFlyZone.vertex3Long)
                .addValue("vertex3Lat", noFlyZone.vertex3Lat)
                .addValue("vertex4Long", noFlyZone.vertex4Long)
                .addValue("vertex4Lat", noFlyZone.vertex4Lat)
                .addValue("maxAltitude", noFlyZone.maxAltitude)
                .addValue("minAltitude", noFlyZone.minAltitude);

        namedParameterJdbcTemplate.update(ADD_POLYGON_NO_FLY_ZONE_SQL, parameterSource);
    }

    /**
     * Adds a new Rectangle type no-fly zone
     *
     * @param noFlyZone The RectangleNoFlyZone Object that provides the values that will be inserted into the DB
     */
    public void addRectangleNoFlyZone(RectangleNoFlyZone noFlyZone) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", noFlyZone.name)
                .addValue("westLongDegree", noFlyZone.westLongDegree)
                .addValue("eastLongDegree", noFlyZone.eastLongDegree)
                .addValue("southLatDegree", noFlyZone.southLatDegree)
                .addValue("northLatDegree", noFlyZone.northLatDegree)
                .addValue("rotationDegree", noFlyZone.rotationDegree)
                .addValue("maxAltitude", noFlyZone.rotationDegree)
                .addValue("minAltitude", noFlyZone.minAltitude);

        namedParameterJdbcTemplate.update(ADD_RECTANGLE_NO_FLY_ZONE_SQL, parameterSource);
    }

    /**
     * Deletes a custom no fly zone
     * @param zoneName The name of the zone to be deleted
     * @return String representing deletion of zone if successfully deleted
     */
    public String deleteNoFlyZone(String zoneName) {
        boolean delete = false;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("zoneName", zoneName);
            namedParameterJdbcTemplate.update(DELETE_ELLIP_NO_FLY_ZONE, parameterSource);
            namedParameterJdbcTemplate.update(DELETE_POLY_NO_FLY_ZONE, parameterSource);
            namedParameterJdbcTemplate.update(DELETE_RECTANGLE_NO_FLY_ZONE, parameterSource);

            return "Deleted No Fly Zone: " + zoneName;

    }
}
