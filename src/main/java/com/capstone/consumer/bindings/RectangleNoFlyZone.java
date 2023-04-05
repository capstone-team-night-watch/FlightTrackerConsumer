package com.capstone.consumer.bindings;

public class RectangleNoFlyZone {

    public String name;
    public String westLongDegree;
    public String eastLongDegree;
    public String southLatDegree;
    public String northLatDegree;
    public String rotationDegree;
    public Long maxAltitude;
    public Long minAltitude;

    @Override
    public String toString() {
        return "RectangleNoFlyZone{" +
                "name='" + name + '\'' +
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
