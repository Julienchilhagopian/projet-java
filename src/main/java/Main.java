import Controller.BoardController;
import View.BoardView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::displayMorpion);
    }

    private static void displayMorpion() {
        JFrame frame = new JFrame("Java Avancée - Morpion Solitaire");
        frame.setSize(new Dimension(700,600));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        frame.setResizable(false);
        
        BoardView board = new BoardView();
        frame.add(board);
        frame.setVisible(true);

        BoardController boardController = BoardController.inst(board);
    }

}
