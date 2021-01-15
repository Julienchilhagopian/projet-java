package Controller;

import Model.Board;
import Model.Point;
import View.BoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        System.out.println(this.boardModel.getActiveVoisins(pointToUpdate));
    }


    private List<Point> getVerticalNeighboursV1(Point inputPoint) {
        List<Point> verticalNeighboursDown = new ArrayList<>();
        verticalNeighboursDown.add(inputPoint);
        int counter = inputPoint.getY() + 1;
        for(Point pt : this.boardModel.getPoints()) {
            if((pt.getX() == inputPoint.getX()) && pt.isActive()) {
                if (pt.getY() == counter) {
                    verticalNeighboursDown.add(pt);
                    counter++;
                }
                if(verticalNeighboursDown.size() == 5) {
                    break;
                }
            }
        }
        if(verticalNeighboursDown.size() < 5) {
            verticalNeighboursDown.clear();
        }

        List<Point> verticalNeighboursUp = new ArrayList<>();
        int counter2 = inputPoint.getY() - 1;
        for(Point pt : this.boardModel.getPoints()) {
            if((pt.getX() == inputPoint.getX()) && pt.isActive()) {
                if (pt.getY() == counter2) {
                    verticalNeighboursUp.add(pt);
                    counter2--;
                }
                if(verticalNeighboursUp.size() == 5) {
                    break;
                }
            }
        }
        if(verticalNeighboursUp.size() < 5) {
         //  verticalNeighboursUp.clear();
        }

        System.out.println("DOWN " + verticalNeighboursDown);
        System.out.println("UP " + verticalNeighboursUp);
        /*
        if(verticalNeighboursDown.size() == 5) {
            return verticalNeighboursDown;
        } else if (verticalNeighboursUp.size() == 5) {
            return verticalNeighboursUp;
        }*/

        return new ArrayList<>();
    }

    private List<Point> getVerticalNeighbours(Point inputPoint) {
        List<Point> verticalNeighbours = new ArrayList<>();
        Point ptToSearch = inputPoint;

        while(searchPoint(ptToSearch).isPresent()) {
            Point voisin = searchPoint(ptToSearch).get();
            verticalNeighbours.add(voisin);
            ptToSearch = voisin;
        }

        return verticalNeighbours;
    }

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
    }


}
