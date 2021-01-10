package View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Board;
import Model.Point;

//sert plus a rien je pense
public class PointView extends JPanel{
	
	
	private JFrame frame;
	private List<Point> points;

	public static PointView create(JFrame mainFrame) {
		return new PointView(mainFrame);
	}
	
	private PointView(JFrame frame) {
		this.frame = frame;
		this.points = new ArrayList<>();
	}

}
