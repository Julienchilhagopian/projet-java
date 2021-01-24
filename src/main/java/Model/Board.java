package Model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Point> points;
    private int count = 1;

    private Board() {
        this.points = this.buildStartList();
    }

    public static Board withClassicBoard() {
        return new Board();
    }

    /**
     * Construction of the starting points from the point of view of the model by making these points active or not on display
     *
     * @return points corresponding to the list of points at initialization
     */
    private List<Point> buildStartList() {
        List<Point> listPoints = new ArrayList<>();
        Integer row = 15;
        Integer column = 15;

        for (int r = 0; r <= row; r++) {
            for (int c = 0; c <= column; c++) {
                Point pt = new Point(r, c);

                if ((r == 3) && (c >= 6 && c <= 9)) {
                    pt.setActive(true);
                } else if (r == 4 && (c == 6 || c == 9)) {
                    pt.setActive(true);
                } else if (r == 5 && (c == 6 || c == 9)) {
                    pt.setActive(true);
                } else if (r == 6 && ((c >= 3 && c <= 6) || ((c >= 9 && c <= 12)))) {
                    pt.setActive(true);
                } else if (r == 7 && (c == 3 || c == 12)) {
                    pt.setActive(true);
                } else if (r == 8 && (c == 3 || c == 12)) {
                    pt.setActive(true);
                } else if (r == 9 && ((c >= 3 && c <= 6) || ((c >= 9 && c <= 12)))) {
                    pt.setActive(true);
                } else if (r == 10 && (c == 6 || c == 9)) {
                    pt.setActive(true);
                } else if (r == 11 && (c == 6 || c == 9)) {
                    pt.setActive(true);
                } else if ((r == 12) && (c >= 6 && c <= 9)) {
                    pt.setActive(true);
                }
                listPoints.add(pt);
            }
        }
        return listPoints;
    }


    public List<Point> getPoints() {
        return points;
    }

    /**
     * Activate point for display in the view
     *
     * @param pointToUpdate corresponding to the point to be activated
     */
    public void setActive(Point pointToUpdate) {
        if (!this.points.contains(pointToUpdate)) {
            throw new IllegalArgumentException("Le point n'est pas dans la liste, c'est un problème.");
        }

        for (Point pt : this.points) {
            if (pt.equals(pointToUpdate)) {
                pt.setActive(true);
                pt.pointNum(count);
                this.count++;
            }
        }
    }

    /**
     * Add a line in the Point model
     *
     * @param trace corresponding to the trace added
     */
    public void setTrace(Trace trace) {
        for (Point tracePoint : trace.getPoints()) {
            for (Point pt : this.points) {
                if (pt.equals(tracePoint)) {
                    pt.addTraces(trace);
                }
            }
        }
    }

    // Method dedicated to testing
    public int countActive() {
        int counter = 0;
        for (Point pt : this.points) {
            if (pt.isActive()) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Update neighboring points around each active point
     */
    public void updateVoisins() {
        for (Point p : this.points) {
            for (Point voisin : this.points) {
                if (voisin.isActive()) {
                    if (voisin.getY() + 1 == p.getY() && voisin.getX() == p.getX()) {
                        p.addNeighbour(voisin);
                    }
                    if (voisin.getY() - 1 == p.getY() && voisin.getX() == p.getX()) {
                        p.addNeighbour(voisin);
                    }
                    if (voisin.getX() + 1 == p.getX() && voisin.getY() == p.getY()) {
                        p.addNeighbour(voisin);
                    }
                    if (voisin.getX() - 1 == p.getX() && voisin.getY() == p.getY()) {
                        p.addNeighbour(voisin);
                    }
                    if (voisin.getX() - 1 == p.getX() && voisin.getY() - 1 == p.getY()) {
                        p.addNeighbour(voisin);
                    }
                    if (voisin.getX() + 1 == p.getX() && voisin.getY() + 1 == p.getY()) {
                        p.addNeighbour(voisin);
                    }
                    if (voisin.getX() - 1 == p.getX() && voisin.getY() + 1 == p.getY()) {
                        p.addNeighbour(voisin);
                    }
                    if (voisin.getX() + 1 == p.getX() && voisin.getY() - 1 == p.getY()) {
                        p.addNeighbour(voisin);
                    }
                }
            }
        }
    }

}
