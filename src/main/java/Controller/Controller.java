package Controller;

import Model.Board;
import View.BoardView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Board boardModel;
    private BoardView view;
    private IController currentController;

    private Controller(Board startModel, BoardView mainView) {
        this.boardModel = startModel;
        this.view = mainView;
        initBoardView();

        this.currentController = this.build5DController();
    }

    public static Controller withDefaultModel(BoardView mainView) {
        Board model = Board.withClassicBoard();

        return new Controller(model, mainView);
    }

    private BoardController build5DController() {
        return BoardController.create(this);
    }

    private void initBoardView() {
        this.view.printPoints(this.boardModel.getPoints());
        this.view.printScore();
        this.view.initMorpionButtons();
        this.view.attachOnClick5D(this.launch5D());
        this.view.attachOnClick5T(this.launch5T());
    }

    public void resetBoardView() {
        this.view.removeOnClickButtonListener();
        this.view.removeOnClickButtonRandomGame();
        this.getView().reset();
        this.resetDefaultModel();
    }

    public void restartBoardView(){
        this.view.printPoints(this.boardModel.getPoints());
        this.view.printScore();
        this.view.attachOnClickButtonListenner(this.currentController.buildClickPointBehavior());
        this.view.attachOnClickButtonRandomGame(this.currentController.buildRandomGame());
    }

    private ActionListener launch5D() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoardView();
                currentController = build5DController();
                System.out.println("launch 5D");
                restartBoardView();
            }
        };
    }

    private ActionListener launch5T() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoardView();
                // set currentController = nouvelle classe 5T
                System.out.println("launch 5T");
                restartBoardView();
            }
        };
    }


    public Board getBoardModel() {
        return boardModel;
    }

    private void resetDefaultModel() {
        this.boardModel = Board.withClassicBoard();
    }

    public void setBoardModel(Board boardModel) {
        this.boardModel = boardModel;
    }

    public BoardView getView() {
        return view;
    }
}
