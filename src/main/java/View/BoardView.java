package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.*;

import Model.Board;
import Model.Point;

public class BoardView extends JPanel {
    private Map<JButton, Point> buttons;
    private List<LineView> line;
    

    public BoardView() {
        this.buttons = new HashMap<>();
        this.line = new ArrayList<>();
    }
    
    public void printLine(int xa, int ya, int xb, int yb) {
    	line.add(new LineView(xa,ya,xb,yb));
    }
    
    
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.drawRect(30, 30, 450, 450);
        g.setColor(Color.GRAY);
        for (int i = 30; i <= 450; i += 30) {
            g.drawLine(i, 30, i, 480);
      
        }

        for (int i = 30; i <= 450; i += 30) {
            g.drawLine(30, i, 480, i);
            
        }
        
        //On affiche chaque ligne 
        for(LineView l:line) {
        	l.draw(g);
		}
       
	}
	
	

    public void printPoints(List<Point> points){
    	
    	int cellX =0;
    	int cellY=0;
        for (Point p : points) {
        	
            cellX = 30 + (p.getX()*30);
            cellY = 30 + (p.getY()*30);
            cellX = cellX - 10 /2;
            cellY = cellY - 10 /2;
            System.out.println(cellX+" "+cellY);

            JButton btn = new JButton("");
            btn.setBounds(cellX, cellY, 10, 10);

            btn.setBackground(Color.BLACK);
            btn.setOpaque(true);


//            if(!p.isActive()) {
//                btn.setOpaque(false);
//                btn.setContentAreaFilled(false);
//                btn.setBorderPainted(false);
//            }

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
    }
}
