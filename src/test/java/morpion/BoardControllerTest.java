package morpion;

import Controller.Controller;
import Controller.BoardController;
import Model.Point;
import Model.Trace;
import Model.Trace5T;
import View.BoardView;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardControllerTest {
    private BoardView view = new BoardView();
    private Controller controller = Controller.withDefaultModel(view);

    @Test
    void testController5D() throws Exception {
        view = new BoardView();
        controller = Controller.withDefaultModel(view);

        Point noTracePoint = controller.getBoardModel().getPoints().get(3);
        JButton button = view.getButton(noTracePoint);
        button.doClick();

        // Il n'est pas possible de tracer sur ce point.
        assertFalse(noTracePoint.isActive());
        assertEquals(0, noTracePoint.getTraces().size());

        // Point éligible pour un trace verticale (6:2)
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
        // voisin suivant en dehors de la trace.
        assertEquals(0, controller.getBoardModel().getPoints().get(103).getTraces().size());


        // Point éligible pour un trace horizontale (10:12)
        traceEligiblePoint = controller.getBoardModel().getPoints().get(172);
        buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        assertTrue(traceEligiblePoint.isActive());
        assertEquals(1, traceEligiblePoint.getTraces().size());
        // Chaque point de la lignée doit posséder une trace.
        assertEquals(1, controller.getBoardModel().getPoints().get(156).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(140).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(124).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(108).getTraces().size());
        // voisin suivant en dehors de la trace.
        assertEquals(0, controller.getBoardModel().getPoints().get(92).getTraces().size());


        // Point éligible pour un trace diagonale (5:5)
        traceEligiblePoint = controller.getBoardModel().getPoints().get(85);
        buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        assertTrue(traceEligiblePoint.isActive());
        assertEquals(1, traceEligiblePoint.getTraces().size());
        // Chaque point de la lignée doit posséder une trace.
        assertEquals(1, controller.getBoardModel().getPoints().get(70).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(55).getTraces().size());

        // On a deja tracé sur le point 100 dans ce test. Il doit posséder 2 traces.
        assertEquals(2, controller.getBoardModel().getPoints().get(100).getTraces().size());
        assertEquals(1, controller.getBoardModel().getPoints().get(115).getTraces().size());

        // voisin suivant en dehors de la trace.
        assertEquals(0, controller.getBoardModel().getPoints().get(145).getTraces().size());

    }

    @Test
    void testController5T() throws Exception {

        // launch du mode 5T
        view.getButton5T().doClick();

        // Trace horizontale sur le point (8:9)
        Point traceEligiblePoint = controller.getBoardModel().getPoints().get(137);
        JButton buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        // Recuperation du point adjacent 5T (7:9)
        Point eligiblePoint = controller.getBoardModel().getPoints().get(121);

        Trace traceHorizontal = new Trace5T("Horizontal");

        // Les points adjacents sont autorisés en 5T.
        assertTrue(eligiblePoint.isEligible(traceHorizontal));


        // Trace verticale sur le point (6:8)
        traceEligiblePoint = controller.getBoardModel().getPoints().get(104);
        buttonEligible = view.getButton(traceEligiblePoint);
        buttonEligible.doClick();

        eligiblePoint = controller.getBoardModel().getPoints().get(121);

        Trace traceVerticale = new Trace5T("Vertical");

        assertTrue(eligiblePoint.isEligible(traceVerticale));
        
    }
}
