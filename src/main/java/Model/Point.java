package Model;

import java.util.*;

public class Point {
	private int x;
	private int y;
	private Boolean isActive;
	private Boolean isTraced;
	private Set<Point> neighbors;
	private String traceOrientation;
	private int num;

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.isActive = false;
		this.isTraced = false;
		this.neighbors = new HashSet<>();
		this.traceOrientation = "";
		this.num=0;
	}
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public void addNeighbour(Point neighbour){
		this.neighbors.add(neighbour);
	}

	public Set<Point> getNeighbors() {
		return neighbors;
	}

	public Optional<Point> getDownNeighbor() {
		Point pt = null;
		for(Point p : this.neighbors) {
			if(this.getY() + 1 == p.getY() && this.getX() == p.getX()) {
				pt = p;
			}
		}

		return Optional.ofNullable(pt);
	}

	public Optional<Point> getUpNeighbor() {
		Point pt = null;
		for(Point p : this.neighbors) {
			if(this.getY() - 1 == p.getY() && this.getX() == p.getX()) {
				pt = p;
			}
		}

		return Optional.ofNullable(pt);
	}

	public Optional<Point> getLeftNeighbor() {
		Point pt = null;
		for(Point p : this.neighbors) {
			if(this.getX() - 1 == p.getX() && this.getY() == p.getY()) {
				pt = p;
			}
		}

		return Optional.ofNullable(pt);
	}

	public Optional<Point> getRightNeighbor() {
		Point pt = null;
		for(Point p : this.neighbors) {
			if(this.getX() + 1 == p.getX() && this.getY() == p.getY()) {
				pt = p;
			}
		}

		return Optional.ofNullable(pt);
	}



	public void setTraced(Boolean traced) {
		isTraced = traced;
	}

	public Boolean isTraced() {
		return isTraced;
	}
	
	public void pointNum(int count) {
		this.num = count;
	}
	public int getNum() {
		return num;
	}

	public void setTraceOrientation(String traceOrientation) {
		this.traceOrientation = traceOrientation;
	}

	public String getTraceOrientation() {
		return traceOrientation;
	}

	public Boolean isTraceEligible(Point pointToCompare) {
		if(!this.isTraced){
			return true;
		} else {
			return !this.traceOrientation.equals(pointToCompare.getTraceOrientation());
		}
	}
}
