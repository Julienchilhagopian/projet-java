package morpion;

import Model.Board;
import Model.Point;
import Model.Trace;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class BoardModelTest {
	private Board board = Board.withClassicBoard();

	@Test
	void testModel() throws Exception {

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


	@Test
	void testModelPoint() throws Exception {
		Trace trace = new Trace("Vertical");

		// Ce point ne possède pas de trace il est éligible à en avoir une.
		Point pointTest = board.getPoints().get(1);
		assertTrue(pointTest.isEligible(trace));

		pointTest.addTraces(trace);

		// Après l'ajout d'une trace vertical le point n'est plus éligible.
		assertFalse(pointTest.isEligible(trace));

		board.updateVoisins();
		// Target du point 3:6
		Point startPoint = board.getPoints().get(54);
		int counter = 1;

		while(startPoint.getDownNeighbor().isPresent()) {
			startPoint = startPoint.getDownNeighbor().get();
			counter++;
		}

		// A partir du point 3:6 inclus on peut descendre 3 fois.
		assertEquals(4, counter);
	}
}
