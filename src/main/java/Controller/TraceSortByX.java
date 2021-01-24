package Controller;

import Model.Point;

import java.util.Comparator;

public class TraceSortByX implements Comparator<Point> {
	
	/**
	 * Compares the x-position of two points
	 * 
	 * @param a1 corresponding to point 1
	 * @param a2 corresponding to point 2
	 * @return This method returns the value zero if (a1.getX()==a2.getX()), if
	 *         (a1.getX() < a2.getX()) then it returns a value less than zero and if
	 *         (a1.getX() > a2.getX()) then it returns a value greater than zero.
	 */
    public int compare(Point a1, Point a2) {
        return Integer.compare(a1.getX(), a2.getX());
    }
}
