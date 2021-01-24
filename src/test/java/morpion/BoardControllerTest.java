package morpion;

import Controller.Controller;
import Controller.BoardController;
import Model.Point;
import Model.Trace;
import View.BoardView;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        Point traceEligiblePoint = controller.getBoardModel().getPoints().get(54);
        JButton buttonEligible = view.getButton(noTracePoint);
        buttonEligible.doClick();

        assertFalse(noTracePoint.isActive());

    }
}
