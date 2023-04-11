package com.capstone.consumer.repository;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.FlightLocation;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.controllers.GetFlightLocationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.*;
import java.lang.*;
import org.postgis.*;

import java.sql.Connection;

@Service
public class Repository {
    private static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);


    protected static final String GET_FLIGHT_LOCATION = "SELECT name FROM public.statesgeojson_polygon WHERE ST_Within(ST_GeomFromText('POINT(:longitude :latitude)'), geom)";
    @Autowired
    private JdbcTemplate namedParameterJdbcTemplate;

    public String getFlightLocation(String longitude, String latitude) {
        LOGGER.warn("INSIDE REPO LONG:" + longitude);
        LOGGER.warn("REPO LAT:" + latitude);
        //This does not work, had to make do with string builder was running into weird errors
//        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
//        .addValue("longitude", longitude)
//        .addValue("latitude", latitude);

        StringBuilder query = new StringBuilder().append("SELECT name FROM public.statesgeojson_polygon WHERE ST_Within(ST_GeomFromText('POINT(");
        query.append(longitude);
        query.append(" ");
        query.append(latitude);
        query.append(")'), geom)");


        LOGGER.warn(query.toString());
        String flightLocation = namedParameterJdbcTemplate.queryForObject(query.toString(), String.class);

        return flightLocation;
    }

    public void addEllipsoidNoFlyZone(EllipsoidNoFlyZone noFlyZone){

        namedParameterJdbcTemplate.update(
                "INSERT INTO ELLIPSOID_NO_FLY_ZONE " +
                        "(ZONE_NAME, LONGITUDE, LATITUDE, ALTITUDE, LONGRADIUS, LATRADIUS, ALTRADIUS)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                noFlyZone.name, noFlyZone.longitude, noFlyZone.latitude, noFlyZone.altitude, noFlyZone.longRadius, noFlyZone.latRadius, noFlyZone.altRadius
        );

    }

    public void addPolygonNoFlyZone(PolygonNoFlyZone noFlyZone){

        namedParameterJdbcTemplate.update(
                "INSERT INTO POLYGON_NO_FLY_ZONE " +
                        "(ZONE_NAME, VERTEX1LONG, VERTEX1LAT, VERTEX2LONG, VERTEX2LAT, VERTEX3LONG, VERTEX3LAT, " +
                        "VERTEX4LONG, VERTEX4LAT, MAXALTITUDE, MINALTITUDE)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                noFlyZone.name, noFlyZone.vertex1Long, noFlyZone.vertex1Lat, noFlyZone.vertex2Long, noFlyZone.vertex2Lat, noFlyZone.vertex3Long,
                noFlyZone.vertex3Lat, noFlyZone.vertex4Long, noFlyZone.vertex4Lat, noFlyZone.maxAltitude, noFlyZone.minAltitude
        );

    }

    public void addRectangleNoFlyZone(RectangleNoFlyZone noFlyZone){

        namedParameterJdbcTemplate.update(
                "INSERT INTO POLYGON_NO_FLY_ZONE " +
                        "(ZONE_NAME, WESTLONGDEGREE, EASTLONGDEGREE, SOUTHLATDEGREE, NORTHLATDEGREE, " +
                        "ROTATIONDEGREE, MAXALTITUDE, MINALTITUDE)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                noFlyZone.name, noFlyZone.westLongDegree, noFlyZone.eastLongDegree, noFlyZone.southLatDegree, noFlyZone.northLatDegree,
                noFlyZone.rotationDegree, noFlyZone.maxAltitude, noFlyZone.minAltitude
        );

    }

}
