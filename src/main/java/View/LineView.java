package View;

import java.awt.*;

import javax.swing.JPanel;

public class LineView extends JPanel {

	private static final long serialVersionUID = 1L;
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

    /**
     * Drawing of each line when adding a point by the player
     *
     * @param g for the graphic
     */
    public void draw(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.RED);
        g2.drawLine(xa, ya, xb, yb);
    }

}
