package Model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Point> points;
    private Integer row = 15;
    private Integer column = 15;
    private int count=1;
    
    private Board() {
        this.points = this.buildStartList();
    }

    public static Board withClassicBoard() {
       return new Board();
   }

    // Construction du plateau de départ d'un point de vue modèle.
    //J'ai modifie la classe pour accèder a cette methode plus facilement mais du coup on changera :) 
    public List<Point> buildStartList() {
        List<Point> points = new ArrayList<>();

        for (int r = 0; r <= row; r++) {
            for (int c = 0; c <= column; c++) {
                Point pt = new Point(r, c);

                //J'ai un peu changer la méthode, j'ai pas trop compris les setactive
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
                } else if ((r == 12 ) && (c >= 6 && c <= 9)) {
                    pt.setActive(true);
                }
                points.add(pt);
            }
        }
        return points;
    }


    public List<Point> getPoints() {
        return points;
    }

    public void setActive(Point pointToUpdate) {
        if(!this.points.contains(pointToUpdate)) {
            throw  new IllegalArgumentException("Le point n'est pas dans la liste, c'est un problème.");
        }

        for(Point pt : this.points) {
            if(pt.equals(pointToUpdate)) {
                pt.setActive(true);
                pt.pointNum(count);
                count++;
            }
        }
    }


    public void setTraced(Point pointToUpdate, String orientation) {
        if(!this.points.contains(pointToUpdate)) {
            throw  new IllegalArgumentException("Le point n'est pas dans la liste, c'est un problème.");
        }

        for(Point pt : this.points) {
            if(pt.equals(pointToUpdate)) {
                pt.setTraced(true);
                pt.setTraceOrientation(orientation);
            }
        }
    }

    // méthode pour test
    public void countActive() {
        int counter = 0;
        for (Point pt : this.points) {
            if(pt.isActive()) {
                counter++;
            }
        }

        System.out.println("Nb points actifs MODEL :" + counter);
    }

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
                }
            }
        }
    }

}
