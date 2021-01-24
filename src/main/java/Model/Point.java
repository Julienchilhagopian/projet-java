package Model;

import java.util.*;

public class Point {
    private int x;
    private int y;
    private Boolean isActive;
    private Set<Point> neighbors;
    private List<Trace> traces;

    private int num;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.isActive = false;
        this.neighbors = new HashSet<>();
        this.traces = new ArrayList<>();
        this.num = 0;
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

    public Boolean isActive() {
        return isActive;
    }

    /**
     * The point is make active for display on the grid
     * @param active boolean to be set
     */
    public void setActive(Boolean active) {
        isActive = active;
    }

    /**
     * We add the point in the list of neighbors
     *  @param neighbour neighbor to add
     */
    public void addNeighbour(Point neighbour) {
        this.neighbors.add(neighbour);
    }

    public Set<Point> getNeighbors() {
        return neighbors;
    }

    /**
     * We look at all the neighbors below
     *
     * @return pt corresponding to the point below
     */
    public Optional<Point> getDownNeighbor() {
        Point pt = null;
        for (Point p : this.neighbors) {
            if (this.getY() + 1 == p.getY() && this.getX() == p.getX()) {
                pt = p;
            }
        }

        return Optional.ofNullable(pt);
    }

    /**
     * We look at all the neighbors above
     *
     * @return pt corresponding to the point above
     */
    public Optional<Point> getUpNeighbor() {
        Point pt = null;
        for (Point p : this.neighbors) {
            if (this.getY() - 1 == p.getY() && this.getX() == p.getX()) {
                pt = p;
            }
        }

        return Optional.ofNullable(pt);
    }

    /**
     * We look at all the neighbors to the left
     *
     * @return pt corresponding to the point on the left
     */
    public Optional<Point> getLeftNeighbor() {
        Point pt = null;
        for (Point p : this.neighbors) {
            if (this.getX() - 1 == p.getX() && this.getY() == p.getY()) {
                pt = p;
            }
        }

        return Optional.ofNullable(pt);
    }

    /**
     * We look at all the neighbors to the right
     *
     * @return pt corresponding to the point on the right
     */
    public Optional<Point> getRightNeighbor() {
        Point pt = null;
        for (Point p : this.neighbors) {
            if (this.getX() + 1 == p.getX() && this.getY() == p.getY()) {
                pt = p;
            }
        }

        return Optional.ofNullable(pt);
    }

    /**
     * We look at all the neighbors to the top right
     *
     * @return pt corresponding to the point on the top right
     */
    public Optional<Point> getUpRightNeighbor() {
        Point pt = null;
        for (Point p : this.neighbors) {
            if (this.getX() - 1 == p.getX() && this.getY() + 1 == p.getY()) {
                pt = p;
            }
        }

        return Optional.ofNullable(pt);
    }

    /**
     * We look at all the neighbors to the bottom left
     *
     * @return pt corresponding to the point on the bottom left
     */
    public Optional<Point> getDownLeftNeighbor() {
        Point pt = null;
        for (Point p : this.neighbors) {
            if (this.getX() + 1 == p.getX() && this.getY() - 1 == p.getY()) {
                pt = p;
            }
        }

        return Optional.ofNullable(pt);
    }

    /**
     * We look at all the neighbors to the top left
     *
     * @return pt corresponding to the point on the top left
     */
    public Optional<Point> getUpLeftNeighbor() {
        Point pt = null;
        for (Point p : this.neighbors) {
            if (this.getX() + 1 == p.getX() && this.getY() + 1 == p.getY()) {
                pt = p;
            }
        }

        return Optional.ofNullable(pt);
    }

    /**
     * We look at all the neighbors to the bottom right
     *
     * @return pt corresponding to the point on the bottom right
     */
    public Optional<Point> getDownRightNeighbor() {
        Point pt = null;
        for (Point p : this.neighbors) {
            if (this.getX() - 1 == p.getX() && this.getY() - 1 == p.getY()) {
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

    /**
     * Find out if the added point allows you to draw a line
     *
     * @param traceInMaking corresponding to the line
     * @return a boolean if the added point allows to draw a line or not
     */
    public Boolean isEligible(Trace traceInMaking) {
        if (this.traces.isEmpty()) {
            return true;
        }
		for (Trace tr : traces) {
		    if (!tr.eligible(traceInMaking, this)) {
		        return false;
		    }
		}
		return true;
    }

    /**
     * List of all current lines
     * @param trace trace to add
     */
    public void addTraces(Trace trace) {
        this.traces.add(trace);
    }

    public List<Trace> getTraces() {
        return this.traces;
    }

}
