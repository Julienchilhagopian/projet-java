package Controller;

import Model.Board;
import View.BoardView;

import java.util.ArrayList;
import java.util.List;

public class BoardController {
    private Board board;
    private BoardView boardView;

    public BoardController(Board boardModel, BoardView view) {
        this.board = boardModel;
        this.boardView = view;
    }

    public Board getModel() {
        return board;
    }

    public static BoardController inst(BoardView view) {
        Board model = Board.withClassicBoard();
        return new BoardController(model, view);
    }

}
