package Model;

import java.util.ArrayList;
import java.util.List;

public class Trace {
    private List<Point> points;
    private String orientation;

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
}
