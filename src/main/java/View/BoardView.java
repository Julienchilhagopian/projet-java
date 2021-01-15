package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.Line2D;
import javax.sound.sampled.Line;

import Model.Board;
import Model.Point;

public class BoardView extends JPanel {
    private Map<JButton, Point> buttons;
    private List<LineView> line;

    public BoardView() {
        this.buttons = new HashMap<>();
    }
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.drawRect(30, 30, 450, 450);

        for (int i = 30; i <= 450; i += 30) {
            g.drawLine(i, 30, i, 480);
        }

        for (int i = 30; i <= 450; i += 30) {
            g.drawLine(30, i, 480, i);
        }

        for(LineView l : line) {
            l.draw(g);
        }
	}

    public void printPoints(List<Point> points){
        for (Point p : points) {
            int cellX = 30 + (p.getX()*30);
            int cellY = 30 + (p.getY()*30);
            cellX = cellX - 10 /2;
            cellY = cellY - 10 /2;

            JButton btn = new JButton("");
            btn.setBounds(cellX, cellY, 10, 10);
            btn.setBorder(new RoundedBorder(50));
            btn.setBackground(Color.BLACK);
            btn.setOpaque(true);

            if(!p.isActive()) {
                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(false);
            }

            this.buttons.put(btn, p);
            this.add(btn);
        }
        repaint();
    }

    public void attachOnClickButtonListenner(ActionListener callback) {
        for (JButton btn : this.buttons.keySet()) {
            btn.addActionListener(callback);
        }
    }

    public void printLine(int xa, int ya, int xb, int yb) {
        line.add(new LineView(xa,ya,xb,yb));
    }


    public Point getPoint(JButton btn) {
        return this.buttons.get(btn);
    }


}
