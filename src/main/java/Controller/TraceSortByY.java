package Controller;

import Model.Point;

import java.util.Comparator;

public class TraceSortByY implements Comparator<Point> {
    public int compare(Point a1, Point a2) {
        return Integer.compare(a1.getY(), a2.getY());
    }
}
