package Controller;

import Model.Point;
import Model.Trace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class BoardController implements IController{
    private Thread randomThread;
    private RandomGame randomBehavior;
    private Controller controller;
    private Trace traceToCreate;


    private BoardController(Controller mainController, Trace traceType) {
        this.controller = mainController;
        this.traceToCreate = traceType;
        initBoardView();
    }

    public static BoardController create(Controller controller, Trace traceType) {
        return new BoardController(controller, traceType);
    }


    /*public static BoardController inst(BoardView view) {
        Board model = Board.withClassicBoard();
        return new BoardController(model, view);
    }
    */

    private void initBoardView() {
        controller.getView().attachOnClickButtonListenner(this.buildClickPointBehavior());
        controller.getView().attachOnClickButtonRandomGame(this.buildRandomGame());

       // randomGame();
    }
    
    public ActionListener buildClickPointBehavior() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btnData = (JButton) e.getSource();
                handleOnClickButton(btnData);   
            }
        };
    }
    
    public ActionListener buildRandomGame() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomGame();
            }
        };
    }

    private void handleOnClickButton(JButton btn) {
        Point pointToUpdate = this.controller.getView().getPoint(btn);
        
        // maj des voisins de tous les points.
        this.controller.getBoardModel().updateVoisins();
        //System.out.println("VOISINS" + pointToUpdate.getNeighbors());

        Trace trace = this.searchTrace(pointToUpdate);

        if(trace.isValid()) {
            this.controller.getBoardModel().setTrace(trace);
        	this.controller.getBoardModel().setActive(pointToUpdate);
            this.controller.getView().numPoint(btn,pointToUpdate);
            this.controller.getView().printNewPoint(pointToUpdate);
            this.controller.getBoardModel().countActive();
            handlePrintTrace(trace);
            this.controller.getView().disableBtn(btn);
        }
        else {
        	controller.getView().erreurMsg();
        }

        this.gameOver();
    }

    private void gameOver() {
        Boolean gameOver = true;

        for(Point p : this.controller.getBoardModel().getPoints()) {
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
            this.controller.getView().gameOver();
            this.controller.resetBoardView();

            if(this.randomThread != null) {
                this.randomThread = null;
                this.randomBehavior.stopRandomGame();
            }

           this.controller.restartBoardView();
        }
    }


    public void randomGame() {
        this.randomBehavior = new RandomGame(this.controller.getBoardModel(), this, this.controller.getView());
        // Essai avec invokeLater
        //SwingUtilities.invokeLater(new RandomGame(this.controller.getBoardModel(, this, this.controller.getView()));

        this.randomThread = new Thread(randomBehavior, "Random Thread");
       randomThread.start();
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
       this.controller.getView().printLine(tracePoints.get(0).getX(), tracePoints.get(0).getY(), tracePoints.get(tracePoints.size() - 1).getX(), tracePoints.get(tracePoints.size() - 1).getY());
    }


    private Trace verticalTrace(Point inputPoint) {
        Trace trace = this.traceToCreate.init("Vertical");
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
        Trace trace = this.traceToCreate.init("Horizontal");
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
        Trace trace = this.traceToCreate.init("DiagonalRight");
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
        Trace trace = this.traceToCreate.init("DiagonalLeft");
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
