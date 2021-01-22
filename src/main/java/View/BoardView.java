package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.Graphics2D;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import Controller.BoardController;
import Controller.Ranking;

import java.awt.geom.Line2D;
import javax.sound.sampled.Line;

import Model.Board;
import Model.Point;

public class BoardView extends JPanel {
    private Map<JButton, Point> buttons;
	private final List<LineView> lines;
    private int score;
    private JLabel scoreTxt; 
	private JLabel msgErreur;
    private JButton jbutton;

	public BoardView() {
        this.buttons = new HashMap<>();
        this.lines = Collections.synchronizedList(new LinkedList<>());
        this.score = 0;
        this.scoreTxt = new JLabel();
        this.msgErreur = new JLabel();
        this.jbutton = new JButton("Random");
    }
	
	public Map<JButton, Point> getButtons() {
		return buttons;
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
        
        g.drawRect(30, 520, 100, 40);

        synchronized (lines) {
            for(LineView l : lines) {
                l.draw(g);
            }
        }
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
            
           repaint();
        }
    }
    
    public void printNewPoint(Point a) {
    	int cellX = 30 + (a.getX()*30);
        int cellY = 30 + (a.getY()*30);
        cellX = cellX - 10 /2;
        cellY = cellY - 10 /2;
        
    	JButton j = new JButton("");
    	j.setBounds(cellX, cellY, 10, 10);
        j.setBackground(Color.GRAY);
        j.setOpaque(true);
    	this.buttons.put(j, a); 
        this.setLayout(null);
        this.add(j);
        
        repaint();
    }
    
    public void printScore() {
    	this.scoreTxt.repaint();
    	setLayout(null);
    	scoreTxt.setBounds(50, 490, 100, 100);
    	scoreTxt.setText("Score : "+this.score);
        this.add(scoreTxt);
    }

	public void attachOnClickButtonListenner(ActionListener callback) {
        for (JButton btn : this.buttons.keySet()) {
            btn.addActionListener(callback);
        }
    }
	
	public void attachOnClickButtonRandomGame(ActionListener callback) {
		jbutton.addActionListener(callback);
    }
	
    public void removeOnClickButtonRandomGame() {
        for( ActionListener al : jbutton.getActionListeners() ) {
            jbutton.removeActionListener( al );
        }
    }

    public void removeOnClickButtonListener() {
        for (JButton btn : this.buttons.keySet()) {
            for( ActionListener al : jbutton.getActionListeners() ) {
                btn.removeActionListener( al );
            }
        }
    }
    
    public int getScore() {
		return score;
	}

    public void printLine(int xa, int ya, int xb, int yb) {
        lines.add(new LineView((xa+1)*30,(ya+1)*30,(xb+1)*30,(yb+1)*30));

    }

    public Point getPoint(JButton btn) {
        return this.buttons.get(btn);
    }
    
    public void numPoint(JButton btn, Point p) {
    	JLabel numtext = new JLabel();
    	int x = btn.getX()-10;
    	int y = btn.getY();
    	setLayout(null);
    	numtext.setBounds(x, y, 50, 30);
    	String s = String.valueOf(p.getNum());
    	numtext.setText(s);
    	this.add(numtext);
    	this.score++;
    	printScore();
    	this.msgErreur.setText("");
    }
    
    public void erreurMsg() {
    	setLayout(null);
    	msgErreur.setBounds(30, 450, 1000, 100);
    	msgErreur.setText("Il n'est pas possible de placer un point ici");
    	msgErreur.setForeground(new Color(255,0,0));
        this.add(msgErreur);
        this.msgErreur.repaint();
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(null,"Game Over !\nScore : "+ this.score);
    }

    public void reset() {
	    this.lines.clear();
	    this.removeAll();
	    this.buttons.clear();
	    this.score = 0;
	    this.repaint();
    }
    
    public void buttonRandomGame() {
    	jbutton.setBounds(140, 520, 100, 40);	
    	this.add(jbutton);
    	repaint();
    }

    public JButton getButton(Point p){
	    JButton button = new JButton();
        for (Map.Entry<JButton, Point> entry : buttons.entrySet()) {
            if (entry.getValue().equals(p)) {
                button = entry.getKey();
            }
        }
        return button;
    }
    
    public void tabScore(List<Ranking> t) {
    	setLayout(null);
    	JLabel nameTabScore = new JLabel();
    	nameTabScore.setBounds(530, 0, 100, 100);
    	nameTabScore.setText("Player rankings");
    	this.add(nameTabScore);
    	
    	int y = 40;
    	
    	for(Ranking s : t) {
    		JLabel playerAndScore = new JLabel();
    		playerAndScore.setText(s.getPseudo()+" : "+s.getScores());
    		playerAndScore.setBounds(530, y, 300, 100);
    		y = y + 20;
    		this.add(playerAndScore);
    	}
    }
    
    public String namePlayer() {   	
    	String input = (String)JOptionPane.showInputDialog(null, "Please enter your nickname","Player Name", JOptionPane.QUESTION_MESSAGE,null,null,"");
    	
    	if(input == null)
    		return "Unknow";
    	
    	return input;  	
    }

}
