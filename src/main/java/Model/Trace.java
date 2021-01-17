package Model;

import java.util.List;

public class Trace {
    private List<Point> points;
    private String orientation;

    public Trace(List<Point> points, String orientation) {
        this.points = points;
        this.orientation = orientation;
    }

    public List<Point> getPoints() {
        return points;
    }

    public String getOrientation() {
        return orientation;
    }
}
