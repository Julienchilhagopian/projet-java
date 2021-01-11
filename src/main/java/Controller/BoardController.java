package Controller;

import Model.Board;
import Model.Point;
import View.BoardView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class BoardController extends JPanel implements MouseListener{

    //private Board board;
    //private BoardView boardView;
	private JPanel jpanel;

//    public BoardController(Board boardModel, BoardView view) {
//        this.board = boardModel;
//        this.boardView = view;
//    }

//    public Board getModel() {
//        return board;
//    }

//    public static BoardController inst(BoardView view) {
//        Board model = Board.withClassicBoard();
//        return new BoardController(model, view);
//    }
	
	public BoardController(JPanel jpanel){
		this.jpanel=jpanel;
		jpanel.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Click: x= "+e.getX()+" y = "+e.getY());
		
		int x = e.getX()/30;
		int y = e.getY()/30;
		
		BoardView b = new BoardView();
		b.addPoint(x, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

}
