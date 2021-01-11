package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Model.Board;
import Model.Point;

public class BoardView extends JPanel{
	
	private List<Point> points;
	
	public BoardView() {
		init();
    }
	public BoardView(List<Point> points) {
		this.points = points;
    }

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

        for (Point p : points) {
            int cellX = 30 + (p.getX()*30);
            int cellY = 30 + (p.getY()*30);
            cellX = cellX - 10 /2;
            cellY = cellY - 10 /2;
            g.drawOval(cellX, cellY, 10, 10);
        }
        
        g.drawRect(30, 30, 450, 450);

        for (int i = 30; i <= 450; i += 30) {
            g.drawLine(i, 30, i, 480);
        }

        for (int i = 30; i <= 450; i += 30) {
            g.drawLine(30, i, 480, i);
        }
	}
	
	public void addPoint(int x, int y) {
		System.out.println(points);
		Point p = new Point(x,y);
		this.points.add(p);
		System.out.println(points);
        repaint();
    }
	
	public void init() {
		Board b = new Board();
		points = b.buildStartList();
		repaint();
	}
}
