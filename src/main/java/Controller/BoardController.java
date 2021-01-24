package Controller;

import Model.Point;
import Model.Trace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class BoardController {
    private RandomGame randomBehavior;
    private String player;
    private Controller controller;
    private Trace traceToCreate;


    private BoardController(Controller mainController, Trace traceType) {
        this.controller = mainController;
        this.traceToCreate = traceType;
        this.player = "";
        this.randomBehavior = new RandomGame(this.controller.getBoardModel(), this, this.controller.getView());
        init();
        player = this.controller.getView().namePlayer();
        controllerInput();
    }

    public static BoardController create(Controller controller, Trace traceType) {
        return new BoardController(controller, traceType);
    }

    private void controllerInput() {
    	if (player == null)
    		System.exit(0);

    	while(player != null) {

        	if(!player.equals("") && player.length()<=15)
        		break;

        	if(player.equals("")) {
        		player = this.controller.getView().namePlayerError();
        		if (player == null)
        			System.exit(0);
        	}

        	if(player.length()>15) {
        		player = this.controller.getView().namePlayerErrorSize();
        		if (player == null)
        			System.exit(0);
        	}
        }
    }

    private void init() {
        readScore();
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
        this.controller.getBoardModel().updateNeighbor();

        Trace trace = this.searchTrace(pointToUpdate);

        if(trace.isValid()) {
            this.controller.getBoardModel().setTrace(trace);
        	this.controller.getBoardModel().setActive(pointToUpdate);
            this.controller.getView().numPoint(btn,pointToUpdate);
            this.controller.getView().printNewPoint(pointToUpdate);
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
                    break;
                }
            }
        }

        handlePrintGameOver(gameOver);
    }

    private void handlePrintGameOver(Boolean gameOver) {
        if(gameOver) {
            this.writeScore();
            this.controller.getView().gameOver();
            this.controller.resetBoardView();

            this.randomBehavior.stopRandomGame();

            this.readScore();
           this.controller.restartBoardView();
        }
    }

    private void randomGame() {
        this.randomBehavior = new RandomGame(this.controller.getBoardModel(), this, this.controller.getView());
        randomBehavior.start();
    }

    private void readScore() {
    	try {
    		File f;

        	if(controller.getVersionName().equals("5D")) {
        		f = new File("PlayerRanking.txt");
        	}else {
        		f = new File("PlayerRanking5T.txt");
        	}

            List<Ranking> tab = new ArrayList<>();
            Scanner scanner=new Scanner(f);
            while (scanner.hasNextLine()) {
			      String line = scanner.nextLine();
			      tab.add(new Ranking(line,";"));
			}

            Collections.sort(tab);
            Collections.reverse(tab);
            this.controller.getView().tabScore(tab,controller.getVersionName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeScore() {
    	File f;
    	File f2;

    	if(controller.getVersionName().equals("5D")) {
    		f = new File("PlayerRanking.txt");
        	f2 = new File("PlayerRankingModif.txt");
    	}
    	else {
    		f = new File("PlayerRanking5T.txt");
        	f2 = new File("PlayerRankingModif5T.txt");
    	}

    	try {
    		BufferedReader br = new BufferedReader(new FileReader(f));
            PrintWriter x2 = new PrintWriter(new FileWriter(f2));
            String line;
            int count = 0;

            while ((line = br.readLine()) != null) {

    			String[] decompose = line.split(";");
    			if(this.player.equals(decompose[0])) {
    				count++;
    				if(this.controller.getView().getScore()>=Integer.parseInt(decompose[1])) {
    					line = decompose[0] +";"+ this.controller.getView().getScore();
    					x2.println(line);
    				}
    				else {
    					line = decompose[0] +";"+ decompose[1];
    					x2.println(line);
    				}
    			}
    			else
    				x2.println(line);
            }
            if(count==0) {
            	x2.println(this.player +";"+ this.controller.getView().getScore());
            }

            br.close();
            x2.close();

            delete(f,f2);

        } catch (IOException e) {
        	System.out.println("Erreur");
        }
    }

    private void delete(File f, File f2) {
    	if(f.delete()) {
    		f2.renameTo(f);
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

            if(foundPoint.isEligible(trace)) {
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

            if(foundPoint.isEligible(trace)) {
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

            if(foundPoint.isEligible(trace)) {
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

            if(foundPoint.isEligible(trace)) {
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

            if(foundPoint.isEligible(trace)) {
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

            if(foundPoint.isEligible(trace)) {
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

            if(foundPoint.isEligible(trace)) {
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

            if(foundPoint.isEligible(trace)) {
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
