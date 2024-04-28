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

package com.capstone.geocode.kdtree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Glasson
 * A KD-Tree implementation to quickly find nearest points
 * Currently implements createKDTree and findNearest as that's all that's required here
 */
public class KDTree<T extends KDNodeComparator<T>> {
    private final KDNode<T> root;

    public KDTree(List<T> items) {
        root = createKDTree(items, 0);
    }

    public T findNearest(T search) {
        return findNearest(root, search, 0).getLocation();
    }

    // Only ever goes to log2(items.length) depth so lack of tail recursion is a non-issue
    private KDNode<T> createKDTree(List<T> items, int depth) {
        if (items.isEmpty()) {
            return null;
        }

        items.sort(items.get(0).getComparator(depth % 3));
        int currentIndex = items.size() / 2;
        return new KDNode<>(
                createKDTree(new ArrayList<>(items.subList(0, currentIndex)), depth + 1),
                createKDTree(new ArrayList<>(items.subList(currentIndex + 1, items.size())), depth + 1),
                items.get(currentIndex)
        );
    }

    private KDNode<T> findNearest(KDNode<T> currentNode, T search, int depth) {
        int direction = search.getComparator(depth % 3).compare(search, currentNode.getLocation());
        var next = (direction < 0) ? currentNode.getLeft() : currentNode.getRight();
        var other = (direction < 0) ? currentNode.getRight() : currentNode.getLeft();
        var best = (next == null) ? currentNode : findNearest(next, search, depth + 1); // Go to a leaf

        if (currentNode.getLocation().squaredDistance(search) < best.getLocation().squaredDistance(search)) {
            best = currentNode; // Set best as required
        }

        if (other != null && currentNode.getLocation().axisSquaredDistance(search, depth % 3) < best.getLocation().squaredDistance(search)) {
            KDNode<T> possibleBest = findNearest(other, search, depth + 1);
            if (possibleBest.getLocation().squaredDistance(search) < best.getLocation().squaredDistance(search)) {
                best = possibleBest;
            }
        }

        return best; // Work back up
    }
}
