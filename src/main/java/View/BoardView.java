package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import Controller.Ranking;

import Model.Point;

public class BoardView extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<JLabel> pointLabels;
	private Map<JButton, Point> buttons;
	private final List<LineView> lines;
	private int score;
	private JLabel scoreTxt;
	private JLabel msgErreur;
	private JButton jbutton;
	private JButton button5D;
	private JButton button5T;
	private JLabel nameTabScore;
	private List<JLabel> playerAndScoreList;

	public BoardView() {
		this.buttons = new HashMap<>();
		this.pointLabels = new ArrayList<>();
		this.lines = new LinkedList<>();
		this.score = 0;
		this.scoreTxt = new JLabel();
		this.msgErreur = new JLabel();
		this.nameTabScore = new JLabel();
		this.playerAndScoreList = new ArrayList<>();
		this.jbutton = new JButton("Random");
		this.button5D = new JButton("5D Mode");
		this.button5T = new JButton("5T Mode");
	}

	/**
	 * Draw the grid
	 *
	 * @param g for the Graphic
	 */
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

		for (LineView l : lines) {
			l.draw(g);
		}
	}

	/**
	 * Display of initialization points when starting the game
	 *
	 * @param points for the list of model points
	 */
	public void printPoints(List<Point> points) {
		for (Point p : points) {
			int cellX = 30 + (p.getX() * 30);
			int cellY = 30 + (p.getY() * 30);
			cellX = cellX - 10 / 2;
			cellY = cellY - 10 / 2;

			JButton btn = new JButton();
			btn.setBounds(cellX, cellY, 10, 10);
			btn.setBackground(Color.GRAY);
			btn.setOpaque(true);

			if (!p.isActive()) {
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

	/**
	 * Display each point added by the player
	 *
	 * @param point for the point to be added
	 */
	public void printNewPoint(Point point) {
		int cellX = 30 + (point.getX() * 30);
		int cellY = 30 + (point.getY() * 30);
		cellX = cellX - 10 / 2;
		cellY = cellY - 10 / 2;

		JButton j = new JButton("");
		j.setBounds(cellX, cellY, 10, 10);
		j.setBackground(Color.GRAY);
		j.setOpaque(true);
		this.buttons.put(j, point);
		this.setLayout(null);
		this.add(j);
	}

	/**
	 * Display of the player's score
	 */
	public void printScore() {
		this.scoreTxt.repaint();
		setLayout(null);
		scoreTxt.setBounds(50, 490, 100, 100);
		scoreTxt.setText("Score : " + this.score);
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

	public void attachOnClick5D(ActionListener callback) {
		button5D.addActionListener(callback);
	}

	public void attachOnClick5T(ActionListener callback) {
		button5T.addActionListener(callback);
	}

	public void removeOnClickButtonRandomGame() {
		for (ActionListener al : jbutton.getActionListeners()) {
			jbutton.removeActionListener(al);
		}
	}

	public void removeOnClickButtonListener() {
		for (JButton btn : this.buttons.keySet()) {
			for (ActionListener al : jbutton.getActionListeners()) {
				btn.removeActionListener(al);
			}
		}
	}

	public int getScore() {
		return score;
	}

	/**
	 * Display each line on the grid
	 *
	 * @param xa for the coordinates of x for point a
	 * @param ya for the coordinates of y for point a
	 * @param xb for the coordinates of x for point b
	 * @param yb for the coordinates of y for point b
	 */
	public void printLine(int xa, int ya, int xb, int yb) {
		LineView lineView = new LineView((xa + 1) * 30, (ya + 1) * 30, (xb + 1) * 30, (yb + 1) * 30);
		this.add(lineView);
		lines.add(lineView);
		repaint();
	}

	public Point getPoint(JButton btn) {
		return this.buttons.get(btn);
	}

	/**
	 * Point number added by the player
	 *
	 * @param btn   for the button corresponding to the position of the added point
	 * @param point for the point added by the player
	 */
	public void numPoint(JButton btn, Point point) {
		JLabel numtext = new JLabel();
		int x = btn.getX() - 10;
		int y = btn.getY();
		setLayout(null);
		numtext.setBounds(x, y, 50, 30);
		String s = String.valueOf(point.getNum());
		numtext.setText(s);
		this.add(numtext);
		this.pointLabels.add(numtext);
		this.score++;
		printScore();
		this.msgErreur.setText("");
	}

	/**
	 * Display of the error message if adding a point is not possible at this
	 * location
	 */
	public void erreurMsg() {
		setLayout(null);
		msgErreur.setBounds(30, 450, 1000, 100);
		msgErreur.setText("Il n'est pas possible de placer un point ici");
		msgErreur.setForeground(new Color(255, 0, 0));
		this.add(msgErreur);
		this.msgErreur.repaint();
	}

	/**
	 * Display of the game over message
	 */
	public void gameOver() {
		JOptionPane.showMessageDialog(null, "Game Over !\nScore : " + this.score);
	}

	/**
	 * Resetting the game grid following a game over or a change of mode
	 */
	public void reset() {
		for (JButton btn : this.buttons.keySet()) {
			this.remove(btn);
		}

		for (JLabel label : this.pointLabels) {
			this.remove(label);
		}

		for (JLabel lab : this.playerAndScoreList) {
			this.remove(lab);
		}

		for (LineView l : this.lines) {
			this.remove(l);
		}

		this.remove(scoreTxt);
		this.remove(this.nameTabScore);
		this.pointLabels.clear();
		this.lines.clear();
		this.buttons.clear();
		this.score = 0;
		this.repaint();
	}

	/**
	 * Display of 5T and 5D mode buttons
	 */
	public void initMorpionButtons() {
		this.buttonRandomGame();
		this.versionButtons();
		repaint();
	}

	private void buttonRandomGame() {
		jbutton.setBounds(140, 520, 100, 40);
		this.add(jbutton);
	}

	private void versionButtons() {
		button5D.setBounds(280, 520, 100, 40);
		button5T.setBounds(380, 520, 100, 40);

		this.add(button5D);
		this.add(button5T);
	}

	/**
	 * Retrieve the button corresponding to the point
	 *
	 * @param point point to find
	 * @return button associated with the point
	 */
	public JButton getButton(Point point) {
		JButton button = new JButton();
		for (Map.Entry<JButton, Point> entry : buttons.entrySet()) {
			if (entry.getValue().equals(point)) {
				button = entry.getKey();
			}
		}
		return button;
	}

	public JButton getButton5T() {
		return button5T;
	}

	public void disableBtn(JButton btn) {
		btn.setEnabled(false);
	}

	/**
	 * Display of player rankings
	 *
	 * @param listRanking for the list of players and their score
	 * @param versionName for the selected game mode
	 */
	public void tabScore(List<Ranking> listRanking, String versionName) {
		setLayout(null);
		this.nameTabScore = new JLabel();
		nameTabScore.setBounds(520, 0, 200, 100);
		nameTabScore.setText("Player rankings " + versionName);
		this.add(nameTabScore);

		int y = 40;

		for (Ranking s : listRanking) {
			JLabel playerAndScore = new JLabel();
			playerAndScore.setText(s.getPseudo() + " : " + s.getScores());
			playerAndScore.setBounds(530, y, 300, 100);
			y = y + 20;
			this.add(playerAndScore);
			this.playerAndScoreList.add(playerAndScore);
		}
	}

	/**
	 * Display of the request for the player's nickname
	 *
	 * @return input corresponding to the entry of the player's nickname
	 */
	public String namePlayer() {
		String input = "";
		input = (String) JOptionPane.showInputDialog(null, "Please enter your nickname (less than 15 characters)",
				"Player Name", JOptionPane.QUESTION_MESSAGE, null, null, "");
		return input;
	}

	/**
	 * Display when input is null
	 *
	 * @return input corresponding to the entry of the player's nickname
	 */
	public String namePlayerError() {
		String input = "";
		input = (String) JOptionPane.showInputDialog(null,
				"<html><div color=red>Your nickname is required. Please enter it here.", "Player Name",
				JOptionPane.QUESTION_MESSAGE, null, null, "");
		return input;
	}

	/**
	 * Display when the entry is greater than 15 characters
	 *
	 * @return input corresponding to the entry of the player's nickname
	 */
	public String namePlayerErrorSize() {
		String input = "";
		input = (String) JOptionPane.showInputDialog(null,
				"<html><div color=red>Your nickname can't be more than 15 characters long. Please enter it here.",
				"Player Name", JOptionPane.QUESTION_MESSAGE, null, null, "");
		return input;
	}

}
