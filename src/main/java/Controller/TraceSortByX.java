package Controller;

import java.util.Comparator;

import Model.Point;

public class TraceSortByX implements Comparator<Point>{

	public int compare(Point a1, Point a2) {
		return new Integer(a1.getX()).compareTo(a2.getX());
	}
}
