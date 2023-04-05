package com.capstone.consumer.bindings;

public class PolygonNoFlyZone {

    public String name;
    public Long vertex1Long;
    public Long vertex1Lat;
    public Long vertex2Long;
    public Long vertex2Lat;
    public Long vertex3Long;
    public Long vertex3Lat;
    public Long vertex4Long;
    public Long vertex4Lat;
    public Long maxAltitude;
    public Long minAltitude;


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
