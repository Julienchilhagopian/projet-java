package Controller;

import Model.Board;
import Model.Trace;
import Model.Trace5T;
import View.BoardView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Board boardModel;
    private BoardView view;
    private IController currentController;
    private String versionName;

    public String getVersionName() {
		return versionName;
	}

	private Controller(Board startModel, BoardView mainView) {
		this.versionName = "5D";
        this.boardModel = startModel;
        this.view = mainView;

        this.currentController = this.buildController(new Trace());
        initBoardView();
    }

    public static Controller withDefaultModel(BoardView mainView) {
        Board model = Board.withClassicBoard();

        return new Controller(model, mainView);
    }

    private BoardController buildController(Trace traceType) {
        return BoardController.create(this, traceType);
    }

    private void initBoardView() {
        this.view.printPoints(this.boardModel.getPoints());
        this.view.printScore();
        this.view.initMorpionButtons();
        this.view.attachOnClick5D(this.launch5D());
        this.view.attachOnClick5T(this.launch5T());
        this.view.attachOnClickButtonListenner(this.currentController.buildClickPointBehavior());
        this.view.attachOnClickButtonRandomGame(this.currentController.buildRandomGame());
    }

    public void resetBoardView() {
        this.view.removeOnClickButtonListener();
        this.view.removeOnClickButtonRandomGame();
        this.getView().reset();
        this.resetDefaultModel();
    }

    public void restartBoardView(){
        this.view.printPoints(this.boardModel.getPoints());
        this.view.printScore();
        this.view.attachOnClickButtonListenner(this.currentController.buildClickPointBehavior());
        this.view.attachOnClickButtonRandomGame(this.currentController.buildRandomGame());
    }

    private ActionListener launch5D() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	versionName = "5D";
                resetBoardView();
                currentController = buildController(new Trace());
                System.out.println("launch 5D");
                restartBoardView();
            }
        };
    }

    private ActionListener launch5T() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	versionName = "5T";
                resetBoardView();
                currentController = buildController(new Trace5T());
                System.out.println("launch 5T");
                restartBoardView();
            }
        };
    }


    public Board getBoardModel() {
        return boardModel;
    }

    private void resetDefaultModel() {
        this.boardModel = Board.withClassicBoard();
    }

    public BoardView getView() {
        return view;
    }
}
