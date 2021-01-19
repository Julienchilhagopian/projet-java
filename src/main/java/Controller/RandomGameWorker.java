package Controller;

import Model.Board;
import Model.Point;
import View.BoardView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGameWorker extends SwingWorker {
    private Board boardModel;
    private BoardController controller;
    private BoardView boardView;
    private boolean randomGameOver;

    public RandomGameWorker(Board boardModel, BoardController controller, BoardView boardView) {
        this.boardModel = boardModel;
        this.controller = controller;
        this.boardView = boardView;
    }

    @Override
    protected Object doInBackground() throws Exception {
        this.boardModel.updateVoisins();
        this.randomGameOver = false;
        while(!randomGameOver){
            List<JButton> buttons = this.possibleButtons();
            if(buttons.size() != 0) {
                Random rand = new Random();
                JButton randomButton = buttons.get(rand.nextInt(buttons.size()));
                randomButton.doClick();
                Thread.sleep(100);
            }
        }
        return null;
    }

    protected void done()
    {
        try
        {
            System.out.println("IM DONE");
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

    public void stopRandomGame(){
        randomGameOver = true;
    }

    public void updateGameWorker(Board boardModel, BoardController controller, BoardView boardView) {
        this.boardModel = boardModel;
        this.controller = controller;
        this.boardView = boardView;
    }
}
