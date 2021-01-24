package Controller.Sort;

import Model.Point;

import java.util.Comparator;

public class TraceSortByY implements Comparator<Point> {

    /**
     * Compares the y-position of two points
     *
     * @param a1 corresponding to point 1
     * @param a2 corresponding to point 2
     * @return This method returns the value zero if (a1.getY()==a2.getY()), if
     * (a1.getY() < a2.getY()) then it returns a value less than zero and if
     * (a1.getY() > a2.getY()) then it returns a value greater than zero.
     */
    public int compare(Point a1, Point a2) {
        return Integer.compare(a1.getY(), a2.getY());
    }
}
