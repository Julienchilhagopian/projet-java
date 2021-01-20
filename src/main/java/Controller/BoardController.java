package Controller;

import Model.Board;
import Model.Point;
import Model.Trace;
import View.BoardView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class BoardController {
    private Board boardModel;
    private BoardView boardView;
    private Thread randomThread;
    private RandomGame randomBehavior;
    private String player;


    private BoardController(Board boardModel, BoardView view) {
        this.boardModel = boardModel;
        this.boardView = view;
        this.player = "";
        initBoardView();    
        player = boardView.namePlayer();
        if(player == null)
        	player = "Unknown";
    }

    public Board getModel() {
        return boardModel;
    }

    public static BoardController inst(BoardView view) {
        Board model = Board.withClassicBoard();
        return new BoardController(model, view);
    }

    private void initBoardView() {
    	readScore();
        boardView.printPoints(this.boardModel.getPoints());
        boardView.buttonRandomGame();
        boardView.printScore();
        boardView.attachOnClickButtonListenner(this.buildClickPointBehavior());
        boardView.attachOnClickButtonRandomGame(this.buildRandomGame());
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
        //System.out.println("VOISINS" + pointToUpdate.getNeighbors());

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
        	this.writeScore();
            this.boardView.gameOver();
            this.boardView.removeOnClickButtonRandomGame();
            this.boardView.removeOnClickButtonListener();
            this.boardView.reset();
            this.boardModel = Board.withClassicBoard();

            if(this.randomThread != null) {
                this.randomThread = null;
                this.randomBehavior.stopRandomGame();
            }

            initBoardView();
        }
    }


    public void randomGame() {
        this.randomBehavior = new RandomGame(this.boardModel, this, this.boardView);
        // Essai avec invokeLater
        //SwingUtilities.invokeLater(new RandomGame(this.boardModel, this, this.boardView));

        this.randomThread = new Thread(randomBehavior, "Random Thread");
        randomThread.start();
    }
    
    public void readScore() {
    	try {
            File f = new File("PlayerRanking.txt");
            List<Ranking> tab = new ArrayList<>();
            Scanner scanner=new Scanner(f);
            while (scanner.hasNextLine()) {
			      String line = scanner.nextLine();
			      tab.add(new Ranking(line,";"));
			}
            Collections.sort(tab);
            Collections.reverse(tab);       
            boardView.tabScore(tab);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeScore() {
    	
    	File f = new File("PlayerRanking.txt");
    	PrintWriter x;
    	Scanner scanner;
		try {
			x = new PrintWriter(new FileWriter(f,true));
			scanner = new Scanner(f);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
		while (scanner.hasNextLine()) 
	    {
			String lineFromFile = scanner.nextLine();
			
			if(lineFromFile.contains(this.player)) {
				String[] decompose = lineFromFile.split(";");
				
				if(this.boardView.getScore()>Integer.parseInt(decompose[1])) {
					int a = Integer.parseInt(decompose[1]);
					int b = this.boardView.getScore();
					a = b;
				}
			}
			else {
				x.println(this.player +";"+ boardView.getScore());				
		    	x.close(); 
			}
			
			
	    }
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
