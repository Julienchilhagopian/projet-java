package morpion;

import Controller.Enum.Directions;
import Model.Board;
import Model.Point;
import Model.Trace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Please enter a pseudo each time a test is launched.
 */
public class BoardModelTest {
    private Board board = Board.withClassicBoard();

    @Test
    void testModel() {

        assertEquals(256, board.getPoints().size());

        int nbActive = board.countActive();
        board.setActive(board.getPoints().get(3));

        assertTrue(board.getPoints().get(3).isActive());
        assertEquals(nbActive + 1, board.countActive());

        assertEquals(0, board.getPoints().get(87).getNeighbors().size());
        board.updateVoisins();
        assertEquals(3, board.getPoints().get(87).getNeighbors().size());

        // Point 3 is active so point 2 must own a neighbor
        assertEquals(1, board.getPoints().get(2).getNeighbors().size());
    }


    @Test
    void testModelPoint() {
        Trace trace = new Trace(Directions.VERTICAL.toString());

        // This point does not own a trace, he is eligible for one.
        Point pointTest = board.getPoints().get(1);
        assertTrue(pointTest.isEligible(trace));

        pointTest.addTraces(trace);

        // After adding a vertical trace, the point is not eligible anymore.
        assertFalse(pointTest.isEligible(trace));

        board.updateVoisins();
        // Targeting point 3:5 to test a descending vertical.
        Point startPoint = board.getPoints().get(53);
        int counter = 1;

        while (startPoint.getDownNeighbor().isPresent()) {
            startPoint = startPoint.getDownNeighbor().get();
            counter++;
        }

        // From point 3:5 included we can go down 3 times.
        assertEquals(5, counter);

        // Targeting point 8:6 to test horizontal move.
        startPoint = board.getPoints().get(134);
        counter = 1;

        while (startPoint.getRightNeighbor().isPresent()) {
            startPoint = startPoint.getRightNeighbor().get();
            counter++;
        }

        assertEquals(5, counter);


        // Targeting point 10:10 to test diagonal move.
        startPoint = board.getPoints().get(170);
        counter = 1;

        while (startPoint.getDownLeftNeighbor().isPresent()) {
            startPoint = startPoint.getDownLeftNeighbor().get();
            counter++;
        }

        // From point  10:10 included we can visit 3 point going down in diagonal
        assertEquals(3, counter);
    }
}
