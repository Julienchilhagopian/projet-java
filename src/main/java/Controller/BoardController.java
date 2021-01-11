package Controller;

import Model.Board;
import View.BoardView;

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
    }


}
