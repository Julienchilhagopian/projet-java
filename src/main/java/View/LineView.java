package View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

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
	public void draw(Graphics g) {
		g.setColor(Color.RED);
        g.drawLine(xa, ya, xb, yb);
	}
	

	

}
