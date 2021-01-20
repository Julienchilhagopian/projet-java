package Controller;

import Model.Board;
import View.BoardView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Board boardModel;
    private BoardView view;

    private Controller(Board startModel, BoardView mainView) {
        this.boardModel = startModel;
        this.view = mainView;
        initBoardView();
    }

    public static Controller withDefaultModel(BoardView mainView) {
        Board model = Board.withClassicBoard();

        return new Controller(model, mainView);
    }

    public BoardController buildBoardController() {
        return BoardController.create(this);
    }

    private void initBoardView() {
        this.view.printPoints(this.boardModel.getPoints());
        this.view.printScore();
        this.view.initMorpionButtons();
        this.view.attachOnClick5D(this.launch5D());
        this.view.attachOnClick5T(this.launch5T());
    }

    private ActionListener launch5D() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("launch 5D");
            }
        };
    }

    private ActionListener launch5T() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("launch 5T");
            }
        };
    }


    public Board getBoardModel() {
        return boardModel;
    }

    public void resetDefaultModel() {
        this.boardModel = Board.withClassicBoard();
    }

    public void setBoardModel(Board boardModel) {
        this.boardModel = boardModel;
    }

    public BoardView getView() {
        return view;
    }
}
