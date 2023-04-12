package com.capstone.consumer.bindings;

import java.util.List;

public class GetNoFlyZonesResponse {

    public List<EllipsoidNoFlyZone> ellipsoidNoFlyZones;
    public List<RectangleNoFlyZone> rectangleNoFlyZones;
    public List<PolygonNoFlyZone> polygonNoFlyZones;

    public List<EllipsoidNoFlyZone> getEllipsoidNoFlyZones() {
        return ellipsoidNoFlyZones;
    }

    public void setEllipsoidNoFlyZones(List<EllipsoidNoFlyZone> ellipsoidNoFlyZones) {
        this.ellipsoidNoFlyZones = ellipsoidNoFlyZones;
    }

    public List<RectangleNoFlyZone> getRectangleNoFlyZones() {
        return rectangleNoFlyZones;
    }

    public void setRectangleNoFlyZones(List<RectangleNoFlyZone> rectangleNoFlyZones) {
        this.rectangleNoFlyZones = rectangleNoFlyZones;
    }

    public List<PolygonNoFlyZone> getPolygonNoFlyZones() {
        return polygonNoFlyZones;
    }

    @Override
    public String toString() {
        return "GetNoFlyZonesResponse{" +
                "ellipsoidNoFlyZones=" + ellipsoidNoFlyZones.toString() +
                ", rectangleNoFlyZones=" + rectangleNoFlyZones.toString() +
                ", polygonNoFlyZones=" + polygonNoFlyZones.toString() +
                '}';
    }

    public void setPolygonNoFlyZones(List<PolygonNoFlyZone> polygonNoFlyZones) {
        this.polygonNoFlyZones = polygonNoFlyZones;
    }
}
