package Model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
    private List<Point> points;
    private Integer row = 15;
    private Integer column = 15;

//    private Board(List<Point> points) {
//        this.points = points;
//    }

    public Board() {
        this.points = this.buildStartList();
    }


//    public static Board withClassicBoard() {
//        return new Board();
//    }

    // Construction du plateau de départ d'un point de vue modèle.
    //J'ai modifie la classe pour accèder a cette methode plus facilement mais du coup on changera :) 
    public List<Point> buildStartList() {
        List<Point> points = new ArrayList<>();

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < column; c++) {
                Point pt = new Point(r, c);

                //J'ai un peu changer la méthode, j'ai pas trop compris les setactive
                if ((r == 3) && (c >= 6 && c <= 9)) {
                    pt.setActive(true);
                    points.add(pt);
                } else if (r == 4 && (c == 6 || c == 9)) {
                    pt.setActive(true);
                    points.add(pt);
                } else if (r == 5 && (c == 6 || c == 9)) {
                    pt.setActive(true);
                    points.add(pt);
                } else if (r == 6 && ((c >= 3 && c <= 6) || ((c >= 9 && c <= 12)))) {
                    pt.setActive(true);
                    points.add(pt);
                } else if (r == 7 && (c == 3 || c == 12)) {
                    pt.setActive(true);
                    points.add(pt);
                } else if (r == 8 && (c == 3 || c == 12)) {
                    pt.setActive(true);
                    points.add(pt);
                } else if (r == 9 && ((c >= 3 && c <= 6) || ((c >= 9 && c <= 12)))) {
                    pt.setActive(true);
                    points.add(pt);
                } else if (r == 10 && (c == 6 || c == 9)) {
                    pt.setActive(true);
                    points.add(pt);
                } else if (r == 11 && (c == 6 || c == 9)) {
                    pt.setActive(true);
                    points.add(pt);
                } else if ((r == 12 ) && (c >= 6 && c <= 9)) {
                    pt.setActive(true);
                    points.add(pt);
                }
                //points.add(pt);
            }
        }
        return points;
    }



}
