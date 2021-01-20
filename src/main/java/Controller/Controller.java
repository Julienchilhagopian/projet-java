package Controller;

import Model.Board;
import View.BoardView;

public class Controller {
    private Board boardModel;
    private BoardView view;

    private Controller(Board startModel, BoardView mainView) {
        this.boardModel = startModel;
        this.view = mainView;
    }

    public static Controller withDefaultModel(BoardView mainView) {
        Board model = Board.withClassicBoard();

        return new Controller(model, mainView);
    }

    public BoardController buildEditionController() {
        return BoardController.create(this);
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
