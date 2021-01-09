import View.BoardView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        new Main().displayMorpion();
    }

    private void displayMorpion() {
        JFrame frame = new JFrame("Java Avancée - Morpion Solitaire");
        frame.setSize(new Dimension(500,500));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoardView board = BoardView.create(frame);

    }

}
