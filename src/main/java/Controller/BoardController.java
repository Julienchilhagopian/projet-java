package Controller;

import Model.Board;
import Model.Point;
import Model.Trace;
import View.BoardView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class BoardController {
    private Board boardModel;
    private BoardView boardView;
    private Thread randomThread;
    private boolean isRandomGameOver;
    private RandomGame randomBehavior;


    private BoardController(Board boardModel, BoardView view) {
        this.boardModel = boardModel;
        this.boardView = view;
        this.isRandomGameOver = false;
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
        boardView.printScore();
        boardView.buttonRandomGame();
        boardView.attachOnClickButtonListenner(this.buildClickPointBehavior());
        boardView.attachOnClickButtonRandomGame(this.buildRandomGame());

        //randomGame();
        System.out.println(this);
    }
    
    private ActionListener buildClickPointBehavior() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btnData = (JButton) e.getSource();
                handleOnClickButton(btnData);   
            }
        };
    }
    
    private ActionListener buildRandomGame() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomGame();
            }
        };
    }

    private void handleOnClickButton(JButton btn) {
        Point pointToUpdate = this.boardView.getPoint(btn);
        
        // maj des voisins de tous les points.
        this.boardModel.updateVoisins();
        System.out.println("VOISINS" + pointToUpdate.getNeighbors());

        Trace trace = this.searchTrace(pointToUpdate);

        if(trace.isValid()) {
            this.boardModel.setTrace(trace);
        	this.boardModel.setActive(pointToUpdate);
            this.boardView.numPoint(btn,pointToUpdate);
            this.boardView.printNewPoint(pointToUpdate);
            this.boardModel.countActive();
            handlePrintTrace(trace);
            
        }
        else {
        	boardView.erreurMsg();
        }

        this.gameOver();
    }

    private void gameOver() {
        Boolean gameOver = true;

        for(Point p : this.boardModel.getPoints()) {
            if(p.getTraces().isEmpty()) {
                if (this.searchTrace(p).isValid()) {
                    gameOver = false;
                }
            }
        }

        handlePrintGameOver(gameOver);
    }

    private void handlePrintGameOver(Boolean gameOver) {
        if(gameOver) {
            this.boardView.gameOver();
            this.boardView.reset();
            this.boardModel = Board.withClassicBoard();


            if(this.randomThread.isAlive()) {
                this.randomBehavior.stop();
            }

            this.isRandomGameOver = true;

            initBoardView();
        }
    }

	/*
    public void randomGame() {
    	
    	//probleme de performance : c'est très long pour les points ajoutés après
    	List<Trace> t = new ArrayList<>();     	
    	List<Point> p = new ArrayList<>();
    	Random rand = new Random();
    	
    	while(true) {
    		t.clear();
    		p.clear();
    		int randomIndex = 0;
    			
	    	for(Point points:this.boardModel.getPoints()) {
	    		if(!points.isActive()) {
	    			this.boardModel.updateVoisins();    	     
	    	        Trace trace = this.searchTrace(points); 
	    	        if(trace.isValid()) {
	    	        	t.add(trace);
	    	        	p.add(points); 	
	    	        }
	    		}
	    	}	    	
	    	if(t.size() == 0) {
	    		boolean over = true;
	    		handlePrintGameOver(over);
	    	}	    
    	
	    	randomIndex = rand.nextInt(t.size());
	        Trace trace = t.get(randomIndex);
	        Point pointTrace = p.get(randomIndex);
	        
	        this.boardModel.setTrace(trace);
	    	this.boardModel.setActive(pointTrace);
	    	
	    	Map<JButton, Point> map = new HashMap<>();
	    	JButton jbutton = new JButton();
	    	map = boardView.getButtons();
	    	for (Entry<JButton, Point> entry : map.entrySet()) {
	            if (entry.getValue().equals(pointTrace)) {
	                jbutton = entry.getKey();
	            }
	        }
	    	
	        this.boardView.numPoint(jbutton,pointTrace);
	        
	        //problème de performance ici
	        this.boardView.printNewPoint(pointTrace);
	        handlePrintTrace(trace);       
    	}
    }
    */


    public void randomGame() {

       this.randomBehavior = new RandomGame(this.boardModel, this, this.boardView);
       this.randomThread = new Thread(randomBehavior, "Random Thread");
       randomThread.start();
        /*
        this.isRandomGameOver = false;
        this.randomThread = new Thread(() -> {
            this.boardModel.updateVoisins();
            while(!this.isRandomGameOver){
                List<JButton> buttons = this.possibleButtons();
                if(buttons.size() != 0) {
                    Random rand = new Random();
                    JButton randomButton = buttons.get(rand.nextInt(buttons.size()));
                    randomButton.doClick();
                }
            }

        });
        this.randomThread.start();

        */

    }

    /*
    private List<JButton> possibleButtons() {
        List<JButton> nextButtons = new ArrayList<>();
        for (Point p : this.boardModel.getPoints()) {
            if(this.searchTrace(p).isValid()) {
                nextButtons.add(this.boardView.getButton(p));
            }
        }

        return nextButtons;
    }
    */

    public boolean isRandomGameOver() {
        return isRandomGameOver;
    }

    public Trace searchTrace(Point pointToUpdate) {
        Trace trace = this.verticalTrace(pointToUpdate);

        // il faut executer une méthode de recherche seulement une après l'autre si la précédente n'a pas trouvé de trace.
        if (trace.isValid()) {
            return trace;
        } else {
            trace = this.horizontalTrace(pointToUpdate);
            if (trace.isValid()) {
                return trace;
            } else {
                trace = this.diagonalRightTrace(pointToUpdate);
                if (trace.isValid()) {
                    return trace;
                } else {
                    trace = this.diagonalLeftTrace(pointToUpdate);
                    if (trace.isValid()) {
                        return trace;
                    }
                }
            }
        }

        return trace;
    }

    private void handlePrintTrace(Trace trace) {
       List<Point> tracePoints = trace.getPoints();
       //System.out.println("TRACE " + trace);
       this.boardView.printLine(tracePoints.get(0).getX(), tracePoints.get(0).getY(), tracePoints.get(tracePoints.size() - 1).getX(), tracePoints.get(tracePoints.size() - 1).getY());
    }


    private Trace verticalTrace(Point inputPoint) {
        Trace trace = new Trace("Vertical");
        Point startPoint = inputPoint;

        // ajout du point de départ.
        trace.getPoints().add(startPoint);

        // Creuser tant qu'il y a un voisin du dessous
        while(startPoint.getDownNeighbor().isPresent()) {
            Point foundPoint = startPoint.getDownNeighbor().get();

            if(foundPoint.isEligibleVertical()) {
                trace.getPoints().add(foundPoint);

                // la trace est terminée
                if(trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByY());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        // si on est la c'est que la trace n'est pas complète
        // je lance une recherche dans l'autre sens.

        // RESET
        startPoint = inputPoint;

        while(startPoint.getUpNeighbor().isPresent()) {
            Point foundPoint = startPoint.getUpNeighbor().get();

            if(foundPoint.isEligibleVertical()) {
                trace.getPoints().add(foundPoint);

                // la trace est terminée
                if(trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByY());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        trace.getPoints().clear();
        // Attention la liste peut ne pas être complète !!
        return trace;
    }


    private Trace horizontalTrace(Point inputPoint) {
        Trace trace = new Trace("Horizontal");
        Point startPoint = inputPoint;

        // ajout du point de départ.
        trace.getPoints().add(startPoint);

        // Creuser tant qu'il y a un voisin du dessous
        while(startPoint.getRightNeighbor().isPresent()) {
            Point foundPoint = startPoint.getRightNeighbor().get();

            if(foundPoint.isEligibleHorizontal()) {
                trace.getPoints().add(foundPoint);

                // la trace est terminée
                if(trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByX());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        // RESET
        startPoint = inputPoint;

        while(startPoint.getLeftNeighbor().isPresent()) {
            Point foundPoint = startPoint.getLeftNeighbor().get();

            if(foundPoint.isEligibleHorizontal()) {
                trace.getPoints().add(foundPoint);

                // la trace est terminée
                if(trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByX());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        trace.getPoints().clear();
        return trace;
    }

    private Trace diagonalRightTrace(Point inputPoint) {
        Trace trace = new Trace("DiagonalRight");
        Point startPoint = inputPoint;

        // ajout du point de départ.
        trace.getPoints().add(startPoint);

        // Creuser tant qu'il y a un voisin du dessous
        while(startPoint.getUpRightNeighbor().isPresent()) {
            Point foundPoint = startPoint.getUpRightNeighbor().get();

            if(foundPoint.isEligibleDiagonalRight()) {
                trace.getPoints().add(foundPoint);

                // la trace est terminée
                if(trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByX());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        // RESET
        startPoint = inputPoint;

        while(startPoint.getDownLeftNeighbor().isPresent()) {
            Point foundPoint = startPoint.getDownLeftNeighbor().get();

            if(foundPoint.isEligibleDiagonalRight()) {
                trace.getPoints().add(foundPoint);

                // la trace est terminée
                if(trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByX());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        trace.getPoints().clear();
        return trace;
    }

    private Trace diagonalLeftTrace(Point inputPoint) {
        Trace trace = new Trace("DiagonalLeft");
        Point startPoint = inputPoint;

        // ajout du point de départ.
        trace.getPoints().add(startPoint);

        // Creuser tant qu'il y a un voisin du dessous
        while(startPoint.getUpLeftNeighbor().isPresent()) {
            Point foundPoint = startPoint.getUpLeftNeighbor().get();

            if(foundPoint.isEligibleDiagonalLeft()) {
                trace.getPoints().add(foundPoint);

                // la trace est terminée
                if(trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByX());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        // RESET
        startPoint = inputPoint;

        while(startPoint.getDownRightNeighbor().isPresent()) {
            Point foundPoint = startPoint.getDownRightNeighbor().get();

            if(foundPoint.isEligibleDiagonalLeft()) {
                trace.getPoints().add(foundPoint);

                // la trace est terminée
                if(trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByX());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        trace.getPoints().clear();
        return trace;
    }

    
   
	
    

}
