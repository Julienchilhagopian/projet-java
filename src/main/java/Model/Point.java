package Model;

import java.util.*;

public class Point {
	private int x;
	private int y;
	private Boolean isActive;
	private Boolean isTraced;
	private Set<Point> neighbors;
	private Boolean hasVerticalTrace;
	private Boolean hasHorizontalTrace;
	private List<Trace> traces;

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
		this.hasHorizontalTrace = false;
		this.hasVerticalTrace = false;
		this.traces = new ArrayList<>();
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

	
	public void pointNum(int count) {
		this.num = count;
	}
	public int getNum() {
		return num;
	}


	public Boolean isTraceEligibleVertical() {
		if(this.traces.isEmpty()){
			return true;
		} else {
			for(Trace tr : traces) {
				if(tr.getOrientation().equals("Vertical")) {
					return false;
				}
			}
			return true;
		}
	}

	public Boolean isTraceEligibleHorizontal() {
		if(this.traces.isEmpty()){
			return true;
		} else {
			for(Trace tr : traces) {
				if(tr.getOrientation().equals("Horizontal")) {
					return false;
				}
			}
			return true;
		}
	}

	public void addTraces(Trace trace) {
		this.traces.add(trace);
	}

	public List<Trace> getTraces() {
		return this.traces;
	}
}
