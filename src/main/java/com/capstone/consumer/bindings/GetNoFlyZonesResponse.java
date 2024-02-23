package com.capstone.consumer.bindings;

import lombok.*;

import java.util.List;


/**
 * Bindings class used to represent all the no-fly zones existing in the database
 */
@Data
public class GetNoFlyZonesResponse {
    private List<EllipsoidNoFlyZone> ellipsoidNoFlyZones;
    private List<RectangleNoFlyZone> rectangleNoFlyZones;
    private List<PolygonNoFlyZone> polygonNoFlyZones;
    private List<MilitaryNoFlyZone> militaryNoFlyZones;
    private List<TfrNotam> tfrNoFlyZones;
}
