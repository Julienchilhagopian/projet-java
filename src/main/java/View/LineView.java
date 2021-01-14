package View;

import java.awt.Graphics;

import javax.swing.JPanel;

public class LineView extends JPanel{

	private int xa;
	private int ya;
	private int xb;
	private int yb;
	
	public LineView(int xa, int ya, int xb, int yb) {
		super();
		this.xa = xa;
		this.ya = ya;
		this.xb = xb;
		this.yb = yb;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("dd");
        g.drawLine(xa, ya, xb, yb);
        repaint();
        
	}
}
