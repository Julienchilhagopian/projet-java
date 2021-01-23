package Controller;

import java.awt.event.ActionListener;

public interface IController {
    ActionListener buildClickPointBehavior();

    ActionListener buildRandomGame();

    void randomGame();
}
