import Controller.Controller;
import View.BoardView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        new Main().displayMorpion();
    }
    
    /**
     * Initialization and configuration of the JFrame window.
     * Calling from the view and the controller
     */
    private void displayMorpion() {
        JFrame frame = new JFrame("Java Avancée - Morpion Solitaire");
        frame.setSize(new Dimension(700, 620));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        BoardView boardView = new BoardView();
        frame.add(boardView);
        frame.setVisible(true);

        Controller.withDefaultModel(boardView);
    }

}
