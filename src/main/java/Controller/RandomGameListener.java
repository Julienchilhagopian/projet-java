package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RandomGameListener implements ActionListener {
    private BoardController currentController;

    public RandomGameListener(BoardController currentController) {
        this.currentController = currentController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.currentController.randomGame();
    }
}
