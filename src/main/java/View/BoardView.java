package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
    private List<LineView> lines;
    private int score;
    private JLabel scoreTxt = new JLabel();

	public BoardView() {
        this.buttons = new HashMap<>();
        this.lines = new ArrayList<>();
        this.score = 0;
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
        
        g.drawRect(30, 500, 100, 40);

        for(LineView l : lines) {
            l.draw(g);
        }
        repaint();
	}

    public void printPoints(List<Point> points){
    	
        for (Point p : points) {
            int cellX = 30 + (p.getX()*30);
            int cellY = 30 + (p.getY()*30);
            cellX = cellX - 10 /2;
            cellY = cellY - 10 /2;

            JButton btn = new JButton();
            btn.setBounds(cellX, cellY, 10, 10);
            btn.setBackground(Color.GRAY);
            btn.setOpaque(true);
            
            if(!p.isActive()) {
                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(false);
            }

            this.buttons.put(btn, p); 
            this.setLayout(null);
            this.add(btn);    
        }
    }
    
    public void printScore() {
    	this.scoreTxt.repaint();
    	setLayout(null);
    	scoreTxt.setBounds(50, 470, 100, 100);
    	scoreTxt.setText("Score : "+this.score);
        this.add(scoreTxt);
    }

	public void attachOnClickButtonListenner(ActionListener callback) {
        for (JButton btn : this.buttons.keySet()) {
            btn.addActionListener(callback);
        }
    }

    public void printLine(int xa, int ya, int xb, int yb) {
        lines.add(new LineView((xa+1)*30,(ya+1)*30,(xb+1)*30,(yb+1)*30));
    }

    public Point getPoint(JButton btn) {
        return this.buttons.get(btn);
    }
    
    public void addPoint(JButton btn, Point p) {
    	
    	JLabel numtext = new JLabel();
    	int x = btn.getX()-10;
    	int y = btn.getY();
    	setLayout(null);
    	numtext.setBounds(x, y, 50, 30);
    	String s=String.valueOf(p.getNum());
    	numtext.setText(s);
    	this.add(numtext);
    	this.score++;
    	printScore();
    }
}
