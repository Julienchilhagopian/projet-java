package View;

import java.awt.*;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class LineView extends JPanel{

    private int xa;
    private int ya;
    private int xb;
    private int yb;
    private Color randomColor;

    public LineView(int xa, int ya, int xb, int yb) {
        super();
        this.xa = xa;
        this.ya = ya;
        this.xb = xb;
        this.yb = yb;
        this.randomColor = this.randomColor();
    }
    public void draw(Graphics g) {
        super.paintComponent(g);

        // pour faire un gros trait faut faire ça jsp pourquoi
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));

        g2.setColor(randomColor);
        g2.drawLine(xa, ya, xb, yb);   //thick
    }

    public Color randomColor() {
        Random rand = new Random();

        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();

        return new Color(red, green, blue);
    }
}
