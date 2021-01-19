package Controller;

import Model.Board;
import Model.Point;
import View.BoardView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGame implements Runnable {
    private Board boardModel;
    private BoardController controller;
    private BoardView boardView;
    private boolean randomGameOver;

    public RandomGame(Board boardModel, BoardController controller, BoardView boardView) {
        this.boardModel = boardModel;
        this.controller = controller;
        this.boardView = boardView;
        this.randomGameOver = false;
    }

    @Override
    public void run() {
        this.boardModel.updateVoisins();
        this.randomGameOver = false;
        while(!randomGameOver){
            List<JButton> buttons = this.possibleButtons();
            if(buttons.size() != 0) {
                Random rand = new Random();
                JButton randomButton = buttons.get(rand.nextInt(buttons.size()));
                randomButton.doClick();
            }
        }
    }

    private List<JButton> possibleButtons() {
        List<JButton> nextButtons = new ArrayList<>();
        for (Point p : this.boardModel.getPoints()) {
            if(controller.searchTrace(p).isValid()) {
                nextButtons.add(this.boardView.getButton(p));
            }
        }

        return nextButtons;
    }

    public void stop(){
        randomGameOver = true;
    }
}
