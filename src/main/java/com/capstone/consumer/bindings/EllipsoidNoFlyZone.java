package com.capstone.consumer.bindings;

public class EllipsoidNoFlyZone {

    public String name;
    public String longitude;
    public String latitude;
    public Long altitude;
    public Long longRadius;
    public Long latRadius;
    public Long altRadius;

    @Override
    public String toString() {
        return "EllipsoidNoFlyZone{" +
                "name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", altitude=" + altitude +
                ", longRadius=" + longRadius +
                ", latRadius=" + latRadius +
                ", altRadius=" + altRadius +
                '}';
    }
}
