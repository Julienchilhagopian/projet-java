package Controller;

import Model.Board;
import Model.Point;
import View.BoardView;
import View.LineView;

import javax.swing.*;
import java.awt.*;
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
        search(pointToUpdate);
        this.boardModel.setActive(pointToUpdate);
        this.boardView.addPoint(btn,pointToUpdate);
        this.boardView.printPoints(this.boardModel.getPoints());
        this.boardModel.countActive();
    }
   
    private void search(Point pointToUpdate) {
    	//recherche des voisins en ligne (il faudra faire en colonne et en diagonal après)
    	List<Point> list = boardModel.getPoints();
    	List<Point> trace = new ArrayList<>();
    	
    	for(int j = 0;j<5;j++) {
	    	for(int i=pointToUpdate.getX()-4+j;i<pointToUpdate.getX()+j;i++) {
	    		for(Point p : list) {
	    			if(p.isActive() && p.getX()==i && p.getY()==pointToUpdate.getY()) {
	    				if(!trace.contains(p)) {
	    					trace.add(p);
	    				}
	    			
	    			}
	    		}
	    	}
    	}
	    	
	    if(trace.size()==4) {
	    		System.out.println(trace);
	    		Point a = trace.get(0);
	        	Point b = trace.get(trace.size()-1);
	        	
	        	List<LineView> l = new ArrayList<>();
	        	l.add(new LineView(a.getX(),a.getY(),b.getX(),b.getY()));
				
		}
	    
    }
}
