package View;

import java.awt.Graphics;

import javax.swing.JPanel;

import Model.Point;

public class PointView extends JPanel{

	private Point point;

	@Override
	public void paintComponent(Graphics g) {
		System.out.println("ddd");
		super.paintComponent(g);
        g.fillOval(point.getX(), point.getY(), 10, 10);
        repaint();
	}

	public PointView(Point point) {
		super();
		this.point = point;
		System.out.println(point.toString());
	}
}
