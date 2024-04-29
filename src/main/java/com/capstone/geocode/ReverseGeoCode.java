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

import com.capstone.geocode.kdtree.KDTree;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Daniel Glasson on 18/05/2014.
 * Uses KD-trees to quickly find the nearest point
 * <p>
 * ReverseGeoCode reverseGeoCode = new ReverseGeoCode(new FileInputStream("c:\\AU.txt"), true);
 * System.out.println("Nearest to -23.456, 123.456 is " + geocode.nearestPlace(-23.456, 123.456));
 */
public class ReverseGeoCode {
    KDTree<GeoName> kdTree;

    /**
     * Parse the raw text geonames file.
     *
     * @param placeNames the text file downloaded from <a href="http://download.geonames.org/export/dump/">...</a>; can not be null.
     * @param majorOnly  only include major cities in KD-tree.
     * @throws IOException          if there is a problem reading the stream.
     * @throws NullPointerException if zippedPlaceNames is {@code null}.
     */
    public ReverseGeoCode(InputStream placeNames, boolean majorOnly) throws IOException {
        createKdTree(placeNames, majorOnly);
    }

    private void createKdTree(InputStream placeNameData, boolean majorOnly) throws IOException {
        var placeNames = new ArrayList<GeoName>();

        // Read the geonames file in the directory
        try (var in = new BufferedReader(new InputStreamReader(placeNameData))) {
            while (true) {
                var str = in.readLine();

                if (str == null) {
                    break;
                }

                var newPlace = new GeoName(str);

                if (!majorOnly || newPlace.isMajorPlace()) {
                    placeNames.add(newPlace);
                }
            }

        }

        kdTree = new KDTree<>(placeNames);
    }

    public GeoName nearestPlace(double latitude, double longitude) {
        return kdTree.findNearest(new GeoName(latitude, longitude));
    }
}
