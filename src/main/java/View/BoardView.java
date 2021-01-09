package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class BoardView extends JPanel{
	private JFrame frame;

	public static BoardView create(JFrame mainFrame) {
		return new BoardView(mainFrame);
	}

	private BoardView(JFrame mainFrame) {
		this.frame = mainFrame;
		this.initBoardView();
	}

	private void initBoardView() {
		this.frame.add(this);

		//Création de la grille
		JPanel grid = new JPanel (new GridLayout (15,15));
		Border blackline = BorderFactory.createLineBorder(Color.black,1);

		//chaque case du tableau
		for(int i = 0; i<225;i++){
			JPanel box = new JPanel();
			box.setBorder(blackline);
			grid.add(box);
		}
		grid.setBorder(blackline);
		frame.add(grid);
		frame.setVisible(true);
	}
}
