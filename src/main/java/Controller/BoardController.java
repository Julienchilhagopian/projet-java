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
import java.util.Collections;
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
        this.boardView.addPoint(btn,pointToUpdate);
        this.boardView.printPoints(this.boardModel.getPoints());
        this.boardModel.countActive();
        
        //ajout des lignes
        // Il faudra faire des conditions d'ajout de point mais plus tard :)
        search(pointToUpdate);
    }
   
    private void search(Point pointToUpdate) {
    	//recherche des voisins en ligne (il faudra faire en colonne et en diagonal après)
    	List<Point> list = boardModel.getPoints();
    	
    	
    	for(int j = 0;j<=5;j++) {
    		List<Point> trace = new ArrayList<>();
    		System.out.println("\n");
	    	for(int i=pointToUpdate.getX()-4+j;i<=pointToUpdate.getX()+j;i++) {
	    		System.out.println(i);
	    		for(Point p : list) {
		    		if(p.isActive() && p.getY()==pointToUpdate.getY() && p.getX()==i){
		    			
		    			if(!trace.contains(p)) {
		    				trace.add(p);
		    				
		    				if(trace.size()==5) {
		    		    		System.out.println(trace);
		    		    		trace.add(pointToUpdate);
		    		    		
		    		    		//on trie la liste des points pour faire une belle ligne
		    		        	Collections.sort(trace, new TraceSortByX());
		    		        	Point a = trace.get(0);
		    		        	Point b = trace.get(trace.size()-1);
		    		        
		    		        	boardView.printLine((a.getX()+1)*30,(a.getY()+1)*30,(b.getX()+1)*30,(b.getY()+1)*30);

		    		    	}
		    			}
		    		}
	    			
	    		}
	    	}
	  
    	}
    	

	    
	
    }
}
