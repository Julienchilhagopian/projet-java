package morpion;

import Controller.Controller;
import Controller.Enum.Directions;
import Model.Point;
import Model.Trace;
import Model.Trace5T;
import View.BoardView;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Please enter a pseudo each time a test is launched.
 */
public class BoardControllerTest {
    private BoardView view = new BoardView();
    private Controller controller = Controller.withDefaultModel(view);

    @Test
    void testController5D() {
        view = new BoardView();
        controller = Controller.withDefaultModel(view);

        Point noTracePoint = controller.getBoardModel().getPoints().get(3);
        JButton button = view.getButton(noTracePoint);
        button.doClick();

        // This point cannot be traced.
        assertFalse(noTracePoint.isActive());
        assertEquals(0, noTracePoint.getTraces().size());

        // This point is eligible for vertical trace (6:2)
        Point traceEligiblePoint = controller.getBoardModel().getPoints().get(98);
        JButton buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        assertTrue(traceEligiblePoint.isActive());
        assertEquals(1, traceEligiblePoint.getTraces().size());

        // Each point must own a trace.
        assertEquals(1, controller.getBoardModel().getPoints().get(99).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(100).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(101).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(102).getTraces().size());
        // Next neighbor outside the trace
        assertEquals(0, controller.getBoardModel().getPoints().get(103).getTraces().size());


        // This point is eligible for horizontal trace  (10:12)
        traceEligiblePoint = controller.getBoardModel().getPoints().get(172);
        buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        assertTrue(traceEligiblePoint.isActive());
        assertEquals(1, traceEligiblePoint.getTraces().size());
        // Each point must own a trace.
        assertEquals(1, controller.getBoardModel().getPoints().get(156).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(140).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(124).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(108).getTraces().size());
        // Next neighbor outside the trace
        assertEquals(0, controller.getBoardModel().getPoints().get(92).getTraces().size());


        // This point is eligible for diagonal trace (5:5)
        traceEligiblePoint = controller.getBoardModel().getPoints().get(85);
        buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        assertTrue(traceEligiblePoint.isActive());
        assertEquals(1, traceEligiblePoint.getTraces().size());
        // Each point must own a trace.
        assertEquals(1, controller.getBoardModel().getPoints().get(70).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(55).getTraces().size());

        // We already pinned point 100. It must own 2 traces.
        assertEquals(2, controller.getBoardModel().getPoints().get(100).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(115).getTraces().size());

        // Next neighbor outside the trace
        assertEquals(0, controller.getBoardModel().getPoints().get(145).getTraces().size());

    }

    @Test
    void testController5T() {

        // launch du mode 5T
        view.getButton5T().doClick();

        // This point is eligible for diagonal trace  (8:9)
        Point traceEligiblePoint = controller.getBoardModel().getPoints().get(137);
        JButton buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        // Retrieving adjacent point 5T (7:9)
        Point eligiblePoint = controller.getBoardModel().getPoints().get(121);

        Trace traceHorizontal = new Trace5T(Directions.HORIZONTAL.toString());

        // Adjacent points are allowed in 5T.
        assertTrue(eligiblePoint.isEligible(traceHorizontal));


        // This point is eligible for vertical trace  (6:8)
        traceEligiblePoint = controller.getBoardModel().getPoints().get(104);
        buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        eligiblePoint = controller.getBoardModel().getPoints().get(121);

        Trace traceVerticale = new Trace5T(Directions.VERTICAL.toString());

        assertTrue(eligiblePoint.isEligible(traceVerticale));

    }
}
