package Model;

import java.util.ArrayList;
import java.util.List;

public class Trace {
    private List<Point> points;
    private String orientation;
    
    public Trace() {
        this.points = new ArrayList<>();
    }

    public Trace(String orientation) {
        this.points = new ArrayList<>();
        this.orientation = orientation;
    }

    public List<Point> getPoints() {
        return points;
    }

    public String getOrientation() {
        return orientation;
    }

    public Boolean isValid() {
        return this.points.size() == 5;
    }

	@Override
	public String toString() {
		return "Trace [points=" + points + ", orientation=" + orientation + "]";
	}


	public boolean isEligible(String orientation) {
        return !this.orientation.equals(orientation);
    }

    public boolean isEligible(String orientation, Point p) {
        if(this.orientation.equals(orientation)) {
            return !this.points.get(2).equals(p);
        } else {
            return true;
        }
    }

}
