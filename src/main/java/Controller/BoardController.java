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

    /**
     * User input control if the entered nickname is empty or more than 15
     * characters long.
     */
    private void controllerInput() {
        if (player == null)
            System.exit(0);

        while (player != null) {

            if (!player.equals("") && player.length() <= 15)
                break;

            if (player.equals("")) {
                player = this.controller.getView().namePlayerError();
                if (player == null)
                    System.exit(0);
            }

            if (player.length() > 15) {
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

            /**
             * Method launched when the users clicks on a non visible point of the grid
             * corresponding to a disabled button
             *
             * @param e corresponding to the action of the click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btnData = (JButton) e.getSource();
                handleOnClickButton(btnData);
            }
        };
    }

    public ActionListener buildRandomGame() {
        return new ActionListener() {

            /**
             * Method launched when the users clicks on the functionality of the random game
             *
             * @param e corresponding to the action of the click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                randomGame();
            }
        };
    }

    /**
     * Tests whether the added points draw a line or not. If yes, the model is
     * informed of the new line and point added and the view displays them. If not,
     * an error message is displayed on the game. In any case the neighbors of the
     * added point are updated.
     *
     * @param btn corresponding to the button of the added point
     */
    private void handleOnClickButton(JButton btn) {
        Point pointToUpdate = this.controller.getView().getPoint(btn);

        this.controller.getBoardModel().updateVoisins();

        Trace trace = this.searchTrace(pointToUpdate);

        if (trace.isValid()) {
            this.controller.getBoardModel().setTrace(trace);
            this.controller.getBoardModel().setActive(pointToUpdate);
            this.controller.getView().numPoint(btn, pointToUpdate);
            this.controller.getView().printNewPoint(pointToUpdate);
            this.controller.getBoardModel().countActive();
            handlePrintTrace(trace);
            this.controller.getView().disableBtn(btn);
        } else {
            controller.getView().erreurMsg();
        }

        this.gameOver();
    }

    /**
     * Method to test if the game is finished. If there are no more traceable points
     * then the gameover variable remains at true and the game is finished.
     */
    private void gameOver() {
        Boolean gameOver = true;

        for (Point p : this.controller.getBoardModel().getPoints()) {
            if (p.getTraces().isEmpty()) {
                if (this.searchTrace(p).isValid()) {
                    gameOver = false;
                    break;
                }
            }
        }

        handlePrintGameOver(gameOver);
    }

    /**
     * Informs the controller that the game is over
     *
     * @param gameOver : true is the game is over, false if not
     */
    private void handlePrintGameOver(Boolean gameOver) {
        if (gameOver) {
            this.writeScore();
            this.controller.getView().gameOver();
            this.controller.resetBoardView();

            this.randomBehavior.stopRandomGame();

            this.readScore();
            this.controller.restartBoardView();
        }
    }

    /**
     * Launching the behavior of the random part
     */
    public void randomGame() {
        this.randomBehavior = new RandomGame(this.controller.getBoardModel(), this, this.controller.getView());

        randomBehavior.start();
    }

    /**
     * Reads the player ranking file, sorts the players from best to least good and
     * calls the view to display it
     */
    public void readScore() {
        try {
            File f;

            if (controller.getVersionName() == "5D") {
                f = new File("PlayerRanking.txt");

            } else {
                f = new File("PlayerRanking5T.txt");
            }

            List<Ranking> tab = new ArrayList<>();
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                tab.add(new Ranking(line, ";"));
            }

            Collections.sort(tab);
            Collections.reverse(tab);
            this.controller.getView().tabScore(tab, controller.getVersionName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writing the player and the score in the ranking file. If the player is
     * already present in the file then the score is just updated.
     */
    public void writeScore() {

        File f;
        File f2;

        if (controller.getVersionName().equals("5D")) {
            f = new File("PlayerRanking.txt");
            f2 = new File("PlayerRankingModif.txt");
        } else {
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
                if (this.player.equals(decompose[0])) {
                    count++;
                    if (this.controller.getView().getScore() >= Integer.parseInt(decompose[1])) {
                        line = decompose[0] + ";" + this.controller.getView().getScore();
                        x2.println(line);
                    } else {
                        line = decompose[0] + ";" + decompose[1];
                        x2.println(line);
                    }
                } else
                    x2.println(line);
            }
            if (count == 0) {
                x2.println(this.player + ";" + this.controller.getView().getScore());
            }

            br.close();
            x2.close();

            delete(f, f2);

        } catch (IOException e) {
            System.out.println("Erreur");
        }
    }

    /**
     * Deleting the old file and renaming the temporary file to the ranking file
     */
    public void delete(File f, File f2) {
        if (f.delete()) {
            f2.renameTo(f);
        }
    }

    /**
     * Execution of a search method one after the other if the previous one did not
     * find a trace.
     *
     * @param pointToUpdate corresponding to the point added
     * @return trace corresponding to the line added according to the direction of
     * the search
     */
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

    /**
     * Calls from the view to draw the line with its coordinates
     *
     * @param trace corresponding to the line to be drawn
     */
    private void handlePrintTrace(Trace trace) {
        List<Point> tracePoints = trace.getPoints();
        // System.out.println("TRACE " + trace);
        this.controller.getView().printLine(tracePoints.get(0).getX(), tracePoints.get(0).getY(),
                tracePoints.get(tracePoints.size() - 1).getX(), tracePoints.get(tracePoints.size() - 1).getY());
    }

    /**
     * Applies a vertical search of a line potentially traceable to the added point.
     *
     * @param inputPoint corresponding to the point added
     * @return trace corresponding to the line added according to the vertical
     * search
     */
    private Trace verticalTrace(Point inputPoint) {
        Trace trace = this.traceToCreate.init("Vertical");
        Point startPoint = inputPoint;

        // Add departure point
        trace.getPoints().add(startPoint);

        // Dig downward while neighbor is present
        while (startPoint.getDownNeighbor().isPresent()) {
            Point foundPoint = startPoint.getDownNeighbor().get();

            if (foundPoint.isEligible(trace)) {
                trace.getPoints().add(foundPoint);

                // Trace is completed
                if (trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByY());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        // Looping on the other way around

        // Starting point reset
        startPoint = inputPoint;

        while (startPoint.getUpNeighbor().isPresent()) {
            Point foundPoint = startPoint.getUpNeighbor().get();

            if (foundPoint.isEligible(trace)) {
                trace.getPoints().add(foundPoint);

                // la trace est terminée
                if (trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByY());
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

    /**
     * Applies a horizontal research of a line potentially traceable to the added
     * point.
     *
     * @param inputPoint corresponding to the point added
     * @return trace corresponding to the line added according to the horizontal
     * research
     */
    private Trace horizontalTrace(Point inputPoint) {
        Trace trace = this.traceToCreate.init("Horizontal");
        Point startPoint = inputPoint;

        trace.getPoints().add(startPoint);

        while (startPoint.getRightNeighbor().isPresent()) {
            Point foundPoint = startPoint.getRightNeighbor().get();

            if (foundPoint.isEligible(trace)) {
                trace.getPoints().add(foundPoint);

                if (trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByX());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        startPoint = inputPoint;

        while (startPoint.getLeftNeighbor().isPresent()) {
            Point foundPoint = startPoint.getLeftNeighbor().get();

            if (foundPoint.isEligible(trace)) {
                trace.getPoints().add(foundPoint);

                if (trace.isValid()) {
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

    /**
     * Applies a right diagonal research of a line potentially traceable to the
     * added point.
     *
     * @param inputPoint corresponding to the point added
     * @return trace corresponding to the line added according to the right diagonal
     * research
     */
    private Trace diagonalRightTrace(Point inputPoint) {
        Trace trace = this.traceToCreate.init("DiagonalRight");
        Point startPoint = inputPoint;

        trace.getPoints().add(startPoint);

        while (startPoint.getUpRightNeighbor().isPresent()) {
            Point foundPoint = startPoint.getUpRightNeighbor().get();

            if (foundPoint.isEligible(trace)) {
                trace.getPoints().add(foundPoint);

                if (trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByX());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        startPoint = inputPoint;

        while (startPoint.getDownLeftNeighbor().isPresent()) {
            Point foundPoint = startPoint.getDownLeftNeighbor().get();

            if (foundPoint.isEligible(trace)) {
                trace.getPoints().add(foundPoint);

                if (trace.isValid()) {
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

    /**
     * Applies a left diagonal research of a line potentially traceable to the added
     * point.
     *
     * @param inputPoint corresponding to the point added
     * @return trace corresponding to the line added according to the left diagonal
     * research
     */
    private Trace diagonalLeftTrace(Point inputPoint) {
        Trace trace = this.traceToCreate.init("DiagonalLeft");
        Point startPoint = inputPoint;

        trace.getPoints().add(startPoint);

        while (startPoint.getUpLeftNeighbor().isPresent()) {
            Point foundPoint = startPoint.getUpLeftNeighbor().get();

            if (foundPoint.isEligible(trace)) {
                trace.getPoints().add(foundPoint);

                if (trace.isValid()) {
                    trace.getPoints().sort(new TraceSortByX());
                    return trace;
                }
            } else {
                break;
            }
            startPoint = foundPoint;
        }

        startPoint = inputPoint;

        while (startPoint.getDownRightNeighbor().isPresent()) {
            Point foundPoint = startPoint.getDownRightNeighbor().get();

            if (foundPoint.isEligible(trace)) {
                trace.getPoints().add(foundPoint);

                if (trace.isValid()) {
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
