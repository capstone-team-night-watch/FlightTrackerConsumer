package com.capstone.consumer.bindings;

public class EllipsoidNoFlyZone {

    public String name;
    public float longitude;
    public float latitude;
    public float altitude;
    public float longRadius;
    public float latRadius;
    public float altRadius;

    public EllipsoidNoFlyZone(String name, float longitude, float latitude, float altitude, float longRadius, float latRadius, float altRadius) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.longRadius = longRadius;
        this.latRadius = latRadius;
        this.altRadius = altRadius;
    }

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
