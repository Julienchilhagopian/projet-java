package morpion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Model.Board;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardModelTest {

	@Test
	void testSomething() throws Exception {
		Board board = Board.withClassicBoard();

		assertEquals(256, board.getPoints().size());

		int nbActive = board.countActive();
		board.setActive(board.getPoints().get(3));

		assertTrue(board.getPoints().get(3).isActive());
		assertEquals(nbActive+1, board.countActive());

		assertEquals(0, board.getPoints().get(87).getNeighbors().size());
		board.updateVoisins();
		assertEquals(3, board.getPoints().get(87).getNeighbors().size());

		// Le point 3 est actif donc le point 2 doit posséder 1 voisin
		assertEquals(1, board.getPoints().get(2).getNeighbors().size());

	}
}
