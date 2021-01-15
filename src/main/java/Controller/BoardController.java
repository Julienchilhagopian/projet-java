package Controller;

import Model.Board;
import Model.Point;
import View.BoardView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BoardController {
    private Board boardModel;
    private BoardView boardView;

    private BoardController(Board boardModel, BoardView view) {
        this.boardModel = boardModel;
        this.boardView = view;
        initBoardView();
    }

    public Board getModel() {
        return boardModel;
    }

    public static BoardController inst(BoardView view) {
        Board model = Board.withClassicBoard();
        return new BoardController(model, view);
    }

    private void initBoardView() {
        boardView.printPoints(this.boardModel.getPoints());
        boardView.attachOnClickButtonListenner(this.buildAddAlternativeBehavior());
    }

    private ActionListener buildAddAlternativeBehavior() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btnData = (JButton) e.getSource();
                handleOnClickButton(btnData);
            }
        };
    }

    private void handleOnClickButton(JButton btn) {
        Point pointToUpdate = this.boardView.getPoint(btn);
        this.boardModel.setActive(pointToUpdate);

        this.boardView.printPoints(this.boardModel.getPoints());
        this.boardModel.countActive();

        // maj des voisins de tous les points.
        this.boardModel.updateVoisins();
        System.out.println(pointToUpdate.getNeighbors());
        System.out.println("TRACE : " + verticalTrace(pointToUpdate));
    }


    private List<Point> verticalTrace(Point inputPoint) {
        List<Point> trace = new ArrayList<>();
        Point startPoint = inputPoint;

        // ajout du point de départ.
        trace.add(startPoint);

        while(startPoint.getDownNeighbor().isPresent()) {
            Point foundPoint = startPoint.getDownNeighbor().get();
            startPoint = foundPoint;

            if(!foundPoint.isTraced()) {
                trace.add(foundPoint);

                // la trace est terminée
                if(trace.size() == 5) {
                    // fin de boucle
                    // Avertir le model des points tracés
                    for(Point pt : trace) {
                        this.boardModel.setTraced(pt);
                    }
                    return trace;
                }
            }
        }


        // si on est la c'est que la trace n'est pas complète
        // je lance une recherche dans l'autre sens.

        // Reset
        trace.clear();
        startPoint = inputPoint;
        trace.add(startPoint);

        while(startPoint.getUpNeighbor().isPresent()) {
            Point foundPoint = startPoint.getUpNeighbor().get();
            startPoint = foundPoint;

            if(!foundPoint.isTraced()) {
                trace.add(foundPoint);

                // la trace est terminée
                if(trace.size() == 5) {
                    // fin de boucle
                    // Avertir le model des points tracés
                    for(Point pt : trace) {
                        this.boardModel.setTraced(pt);
                    }
                    return trace;
                }
            }
        }

        trace.clear();
        // Attention la liste peut ne pas être complète !!
        return trace;
    }

    /*
    private List<Point> getVerticalNeighbours(Point inputPoint) {
        List<Point> verticalNeighbours = new ArrayList<>();
        Point ptToSearch = inputPoint;

        while(searchPoint(ptToSearch).isPresent()) {
            Point voisin = searchPoint(ptToSearch).get();
            verticalNeighbours.add(voisin);
            ptToSearch = voisin;
        }

        return verticalNeighbours;
    } */

    /*
    public Optional<Point> searchPoint(Point pointToSearch) {
        Point point = null;

        for(Point p : this.boardModel.getActiveVoisins(pointToSearch)) {
            if(p.getX() == pointToSearch.getX()) {
                if(p.getY() == pointToSearch.getY() + 1){
                    point = p;
                }
            }
        }

        return Optional.ofNullable(point);
    } */


}
