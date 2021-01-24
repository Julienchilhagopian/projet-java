package Controller;

import Model.Board;
import Model.Point;
import View.BoardView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGame {
	private Board boardModel;
	private BoardController controller;
	private BoardView boardView;
	private boolean randomGameOver;

	public RandomGame(Board boardModel, BoardController controller, BoardView boardView) {
		this.boardModel = boardModel;
		this.controller = controller;
		this.boardView = boardView;
		this.randomGameOver = false;
	}

	/**
	 * Method to make a game randomly. The possible points to add are in a table and
	 * they are added one by one in a random way. The method ends when the
	 * randomGameOver is set to true.
	 */
	public void start() {
		this.boardModel.updateVoisins();
		this.randomGameOver = false;
		List<JButton> buttons = new ArrayList<>();
		while (!randomGameOver) {
			buttons = this.possibleButtons();
			if (buttons.size() != 0) {
				Random rand = new Random();
				JButton randomButton = buttons.get(rand.nextInt(buttons.size()));
				randomButton.doClick();
			}
		}
	}

	/**
	 * Calculation of all possible points to be added at each step
	 */
	private List<JButton> possibleButtons() {
		List<JButton> nextButtons = new ArrayList<>();
		for (Point p : this.boardModel.getPoints()) {
			if (controller.searchTrace(p).isValid()) {
				nextButtons.add(this.boardView.getButton(p));
			}
		}

		return nextButtons;
	}

	public void stopRandomGame() {
		randomGameOver = true;
	}

	public void restartRandomGame() {
		randomGameOver = false;
	}

}
