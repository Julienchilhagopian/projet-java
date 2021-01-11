import Controller.BoardController;
import View.BoardView;
import View.PointView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        new Main().displayMorpion();
    }

    private void displayMorpion() {
        JFrame frame = new JFrame("Java Avancée - Morpion Solitaire");
        frame.setSize(new Dimension(600,600));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   

        BoardView board = new BoardView();
        frame.add(board);
        frame.setVisible(true);

        BoardController boardController = BoardController.inst(board);

    }

}
