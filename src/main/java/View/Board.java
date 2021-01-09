package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Model.Point;
import View.Board;
import View.PointView;

public class Board extends JPanel{

	public static void main(String[] args) {
		JFrame frame = new JFrame("Java Avancée - Morpion Solitaire");
		frame.setSize(new Dimension(500,500));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Board board = new Board();
		frame.add(board);
		
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

		//Afficher les points pour faire la croix
//		Point p = new Point(130,130);
//		PointView pointview = new PointView(p);
//		frame.add(pointview);
	}
}
