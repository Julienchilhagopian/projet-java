package morpion;

import Controller.Controller;
import Controller.BoardController;
import Model.Point;
import Model.Trace;
import View.BoardView;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardControllerTest {

    @Test
    void testController() throws Exception {
        BoardView view = new BoardView();
        Controller controller = Controller.withDefaultModel(view);

        Point noTracePoint = controller.getBoardModel().getPoints().get(3);
        JButton button = view.getButton(noTracePoint);
        button.doClick();

        // Il n'est pas possible de tracer sur ce point.
        assertFalse(noTracePoint.isActive());
        assertEquals(0, noTracePoint.getTraces().size());

        // Point éligible pour un trace verticale
        Point traceEligiblePoint = controller.getBoardModel().getPoints().get(98);
        JButton buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        assertTrue(traceEligiblePoint.isActive());
        assertEquals(1, traceEligiblePoint.getTraces().size());

        // Chaque point de la lignée doit posséder une trace.
        assertEquals(1, controller.getBoardModel().getPoints().get(99).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(100).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(101).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(102).getTraces().size());
        assertEquals(0, controller.getBoardModel().getPoints().get(103).getTraces().size());
    }
}
