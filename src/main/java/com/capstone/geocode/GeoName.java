/*
The MIT License (MIT)
[OSI Approved License]
The MIT License (MIT)

Copyright (c) 2014 Daniel Glasson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.capstone.geocode;

import com.capstone.geocode.kdtree.KDNodeComparator;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Daniel Glasson on 18/05/2014.
 * This class works with a placenames files from http://download.geonames.org/export/dump/
 */

@Setter
public class GeoName extends KDNodeComparator<GeoName> {
    @Getter
    private String name;

    @Getter
    private boolean majorPlace; // Major or minor place

    @Getter
    private double latitude;

    @Getter
    private double longitude;

    @Getter
    String[] source;

    @Getter
    private double[] point = new double[3]; // The 3D coordinates of the point


    @Getter
    private String country;

    GeoName(String data) {
        String[] names = data.split("\t");

        setSource(names);
        setName(names[1]);
        setMajorPlace(names[6].equals("P"));
        setLatitude(Double.parseDouble(names[4]));
        setLongitude(Double.parseDouble(names[5]));
        setPoint();
        setCountry(names[8]);
    }

    GeoName(Double latitude, Double longitude) {
        setName("Search");
        setCountry("Search");

        this.setLatitude(latitude);
        this.setLongitude(longitude);
        setPoint();
    }

    private void setPoint() {
        getPoint()[0] = cos(toRadians(getLatitude())) * cos(toRadians(getLongitude()));
        getPoint()[1] = cos(toRadians(getLatitude())) * sin(toRadians(getLongitude()));
        getPoint()[2] = sin(toRadians(getLatitude()));
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    protected double squaredDistance(GeoName other) {
        double x = this.getPoint()[0] - other.getPoint()[0];
        double y = this.getPoint()[1] - other.getPoint()[1];
        double z = this.getPoint()[2] - other.getPoint()[2];
        return (x * x) + (y * y) + (z * z);
    }

    @Override
    protected double axisSquaredDistance(GeoName other, int axis) {
        double distance = getPoint()[axis] - other.getPoint()[axis];
        return distance * distance;
    }

    @Override
    protected Comparator<GeoName> getComparator(int axis) {
        if (axis == 0) {
            return (a, b) -> Double.compare(a.getPoint()[0], b.getPoint()[0]);
        }

        if (axis == 1) {
            return (a, b) -> Double.compare(a.getPoint()[1], b.getPoint()[1]);
        }

        if (axis == 2) {
            return (a, b) -> Double.compare(a.getPoint()[2], b.getPoint()[2]);
        }

        throw new IllegalArgumentException("Provided axis is not supported");
    }
}
