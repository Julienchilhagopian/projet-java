package Model;

import java.util.ArrayList;
import java.util.List;

public class Trace {
    private List<Point> points;
    private String orientation;
    
    public Trace() {
        this.points = new ArrayList<>();
        this.orientation = "Default";
    }

    public Trace(String orientation) {
        this.points = new ArrayList<>();
        this.orientation = orientation;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Trace init(String orientation) {
        return new Trace(orientation);
    }

    public Boolean isValid() {
        return this.points.size() == 5;
    }

	@Override
	public String toString() {
		return "Trace [points=" + points + ", orientation=" + orientation + "]";
	}

    public boolean eligible(Trace traceInMaking, Point p) {
        return !this.orientation.equals(traceInMaking.getOrientation());
    }

    public String getOrientation() {
        return orientation;
    }

}
