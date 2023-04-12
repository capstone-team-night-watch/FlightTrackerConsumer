package com.capstone.consumer.bindings;

public class RectangleNoFlyZone {

    public String zone_name;
    public float westLongDegree;
    public float eastLongDegree;
    public float southLatDegree;
    public float northLatDegree;

    public RectangleNoFlyZone(String zone_name, float westLongDegree, float eastLongDegree, float southLatDegree, float northLatDegree, float rotationDegree, float maxAltitude, float minAltitude) {
        this.zone_name = zone_name;
        this.westLongDegree = westLongDegree;
        this.eastLongDegree = eastLongDegree;
        this.southLatDegree = southLatDegree;
        this.northLatDegree = northLatDegree;
        this.rotationDegree = rotationDegree;
        this.maxAltitude = maxAltitude;
        this.minAltitude = minAltitude;
    }

    public float rotationDegree;
    public float maxAltitude;
    public float minAltitude;

    @Override
    public String toString() {
        return "RectangleNoFlyZone{" +
                "zone_name='" + zone_name + '\'' +
                ", westLongDegree='" + westLongDegree + '\'' +
                ", eastLongDegree='" + eastLongDegree + '\'' +
                ", southLatDegree='" + southLatDegree + '\'' +
                ", northLatDegree='" + northLatDegree + '\'' +
                ", rotationDegree='" + rotationDegree + '\'' +
                ", maxAltitude=" + maxAltitude +
                ", minAltitude=" + minAltitude +
                '}';
    }
}
