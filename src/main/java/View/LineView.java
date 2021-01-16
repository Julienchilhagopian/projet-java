package View;

import java.awt.*;
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
        super.paintComponent(g);

        // pour faire un gros trait faut faire ça jsp pourquoi
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.RED);
        g2.drawLine(xa, ya, xb, yb);   //thick
        repaint();
    }

}
