package com.capstone.consumer.bindings;

public class PolygonNoFlyZone {

    public String name;
    public float vertex1Long;
    public float vertex1Lat;
    public float vertex2Long;
    public float vertex2Lat;
    public float vertex3Long;
    public float vertex3Lat;
    public float vertex4Long;
    public float vertex4Lat;
    public float maxAltitude;

    public PolygonNoFlyZone(String name, float vertex1Long, float vertex1Lat, float vertex2Long, float vertex2Lat, float vertex3Long, float vertex3Lat, float vertex4Long, float vertex4Lat, float maxAltitude, float minAltitude) {
        this.name = name;
        this.vertex1Long = vertex1Long;
        this.vertex1Lat = vertex1Lat;
        this.vertex2Long = vertex2Long;
        this.vertex2Lat = vertex2Lat;
        this.vertex3Long = vertex3Long;
        this.vertex3Lat = vertex3Lat;
        this.vertex4Long = vertex4Long;
        this.vertex4Lat = vertex4Lat;
        this.maxAltitude = maxAltitude;
        this.minAltitude = minAltitude;
    }

    public float minAltitude;


    @Override
    public String toString() {
        return "PolygonNoFlyZone{" +
                "name='" + name + '\'' +
                ", vertex1Long=" + vertex1Long +
                ", vertex1Lat=" + vertex1Lat +
                ", vertex2Long=" + vertex2Long +
                ", vertex2Lat=" + vertex2Lat +
                ", vertex3Long=" + vertex3Long +
                ", vertex3Lat=" + vertex3Lat +
                ", vertex4Long=" + vertex4Long +
                ", vertex4Lat=" + vertex4Lat +
                ", maxAltitude=" + maxAltitude +
                ", minAltitude=" + minAltitude +
                '}';
    }
}
