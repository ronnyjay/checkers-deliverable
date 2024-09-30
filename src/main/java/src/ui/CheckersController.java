package src.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import src.core.CheckersComputerPlayer;
import src.core.CheckersLogic;
import src.core.CheckersLogic.PlayerType;
import src.core.CheckersLogic.Point;

/**
 * FXMLController for the CheckersGUI
 */
public class CheckersController
{
    /**
     * The GUI's start button
     */
    @FXML
    protected Button START_BTN    = new Button();
    /**
     * The GUI's quit button
     */
    @FXML
    protected Button QUIT_BTN     = new Button();
    /**
     * The GUI's reset button
     */
    @FXML
    protected Button RESET_BTN    = new Button();
    /**
     * The GUI's opponent button
     */
    @FXML
    protected Button OPPONENT_BTN = new Button();
    /**
     * The GUI's computer button
     */
    @FXML
    protected Button COMPUTER_BTN = new Button();

    /**
     * The GUI's representation of the game piece X_0
     */
    @FXML
    protected Circle X_0 = new Circle();

    /**
     * The GUI's representation of the game piece X_1
     */
    @FXML
    protected Circle X_1 = new Circle();

    /**
     * The GUI's representation of the game piece X_2
     */
    @FXML
    protected Circle X_2 = new Circle();

    /**
     * The GUI's representation of the game piece X_3
     */
    @FXML
    protected Circle X_3 = new Circle();

    /**
     * The GUI's representation of the game piece X_4
     */
    @FXML
    protected Circle X_4 = new Circle();

    /**
     * The GUI's representation of the game piece X_5
     */
    @FXML
    protected Circle X_5 = new Circle();

    /**
     * The GUI's representation of the game piece X_6
     */
    @FXML
    protected Circle X_6 = new Circle();

    /**
     * The GUI's representation of the game piece X_7
     */
    @FXML
    protected Circle X_7 = new Circle();

    /**
     * The GUI's representation of the game piece X_8
     */
    @FXML
    protected Circle X_8 = new Circle();

    /**
     * The GUI's representation of the game piece X_9
     */
    @FXML
    protected Circle X_9 = new Circle();

    /**
     * The GUI's representation of the game piece X_10
     */
    @FXML
    protected Circle X_10 = new Circle();

    /**
     * The GUI's representation of the game piece X_11
     */
    @FXML
    protected Circle X_11 = new Circle();

    /**
     * The GUI's representation of the game piece Y_0
     */
    @FXML
    protected Circle Y_0 = new Circle();
    /**
     * The GUI's representation of the game piece Y_1
     */
    @FXML
    protected Circle Y_1 = new Circle();

    /**
     * The GUI's representation of the game piece Y_2
     */
    @FXML
    protected Circle Y_2 = new Circle();

    /**
     * The GUI's representation of the game piece Y_3
     */
    @FXML
    protected Circle Y_3 = new Circle();

    /**
     * The GUI's representation of the game piece Y_4
     */
    @FXML
    protected Circle Y_4 = new Circle();

    /**
     * The GUI's representation of the game piece Y_5
     */
    @FXML
    protected Circle Y_5 = new Circle();

    /**
     * The GUI's representation of the game piece Y_6
     */
    @FXML
    protected Circle Y_6 = new Circle();

    /**
     * The GUI's representation of the game piece Y_7
     */
    @FXML
    protected Circle Y_7 = new Circle();

    /**
     * The GUI's representation of the game piece Y_8
     */
    @FXML
    protected Circle Y_8 = new Circle();

    /**
     * The GUI's representation of the game piece Y_9
     */
    @FXML
    protected Circle Y_9 = new Circle();

    /**
     * The GUI's representation of the game piece Y_10
     */
    @FXML
    protected Circle Y_10 = new Circle();

    /**
     * The GUI's representation of the game piece Y_11
     */
    @FXML
    protected Circle Y_11 = new Circle();

    /**
     * The GUI's win text
     */
    @FXML
    protected TextField WIN_TEXT      = new TextField();
    /**
     * The GUI's tie text
     */
    @FXML
    protected TextField TIE_TEXT      = new TextField();
    /**
     * The GUI's turn text
     */
    @FXML
    protected TextField TURN_TEXT     = new TextField();
    /**
     * The GUI's opponent text
     */
    @FXML
    protected TextField OPPONENT_TEXT = new TextField();

    /**
     * Callback for opponent select buttons
     *
     * @param e The mouse event associated with the callback
     */
    @FXML
    private void onOpponentSelected(MouseEvent e)
    {
        Button btn = (Button) e.getSource();

        if (btn.getId().equals("COMPUTER_BTN"))
        {
            CheckersGUI.GAME.setOpponentComputer();
        }

        OPPONENT_BTN.setVisible(false);
        OPPONENT_BTN.setDisable(true);

        COMPUTER_BTN.setVisible(false);
        COMPUTER_BTN.setDisable(true);

        OPPONENT_TEXT.setVisible(false);

        CheckersGUI.GAME.setRunning(true);

        CheckersGUI.update();
    }

    /**
     * Callback for the reset button
     *
     * @param e The mouse event assoicated with the callback
     */
    @FXML
    void onResetPressed(MouseEvent e)
    {
        RESET_BTN.setVisible(false);
        RESET_BTN.setDisable(true);

        START_BTN.setVisible(true);
        START_BTN.setDisable(false);

        CheckersGUI.reset();

        WIN_TEXT.setVisible(false);
        TURN_TEXT.setVisible(false);
        TIE_TEXT.setVisible(false);

    }

    /**
     * Callback for the start button
     *
     * @param e The mouse event associated with the callback
     */
    @FXML
    private void onStartPressed(MouseEvent e)
    {
        START_BTN.setVisible(false);
        START_BTN.setDisable(true);

        OPPONENT_BTN.setVisible(true);
        OPPONENT_BTN.setDisable(false);

        COMPUTER_BTN.setVisible(true);
        COMPUTER_BTN.setDisable(false);

        OPPONENT_TEXT.setVisible(true);
    }

    /**
     * Callback for the application's quit button
     *
     * @param e
     */
    @FXML
    private void onQuitPressed(MouseEvent e)
    {
        Platform.exit();
    }

    /**
     * Callback for the press of a game piece
     *
     * @param e The mouse event associated with the callback
     */
    @FXML
    private void onCircleClick(MouseEvent e)
    {
        if (!CheckersGUI.GAME.isRunning())
        {
            return;
        }

        Circle clickedCircle = (Circle) e.getSource();

        int y = (int) clickedCircle.getLayoutY();
        int x = (int) clickedCircle.getLayoutX();

        m_offsetX = e.getSceneX() - clickedCircle.getLayoutX();
        m_offsetY = e.getSceneY() - clickedCircle.getLayoutY();

        m_lastPoint = CheckersGUI.pointFromWorldSpace(new Point(x, y));

        if (CheckersGUI.GAME.getCurrentPlayer().hasPieceAt(m_lastPoint))
        {
            clickedCircle.setScaleX(1.05);
            clickedCircle.setScaleY(1.05);
        }

        CheckersGUI.TURN_OVER = false;
    }

    /**
     * Callback for the drag of a game piece
     *
     * @param e The mouse event associated with the callback
     */
    @FXML
    private void onCircleDragged(MouseEvent e)
    {
        if (!CheckersGUI.GAME.isRunning())
        {
            return;
        }

        Circle clickedCircle = (Circle) e.getSource();

        var x = e.getSceneX() - m_offsetX;
        var y = e.getSceneY() - m_offsetY;

        var c = CheckersGUI.pointFromWorldSpace(new CheckersLogic.Point((int) x, (int) y));
        var s = CheckersGUI.pointToWorldSpace(c);

        var dx = c.x - m_lastPoint.x;
        var dy = c.y - m_lastPoint.y;

        if (Math.abs(dx) != Math.abs(dy))
        {
            return;
        }

        // @formatter:off
        try
        {
            CheckersGUI.GAME.handleMove(new CheckersLogic.Move(m_lastPoint, c));
        } 
        catch (Exception err)
        {
            System.err.println(err.getMessage());
            return;
        }
        // @formatter:on

        clickedCircle.setLayoutX(s.x);
        clickedCircle.setLayoutY(s.y);

        CheckersGUI.TURN_OVER = true;

        CheckersGUI.update();
    }

    /**
     * Callback for the release of a game piece
     *
     * @param e The mouse event associated with the callback
     */
    @FXML
    private void onCircleReleased(MouseEvent e)
    {
        if (!CheckersGUI.GAME.isRunning())
        {
            return;
        }

        Circle clickedCircle = (Circle) e.getSource();

        clickedCircle.setScaleX(1.0);
        clickedCircle.setScaleY(1.0);

        if (CheckersGUI.GAME.getCurrentPlayer().getType() == PlayerType.COMPUTER)
        {
            ((CheckersComputerPlayer) CheckersGUI.GAME.getCurrentPlayer()).move(CheckersGUI.GAME);
        }

        CheckersGUI.TURN_OVER = false;

        CheckersGUI.update();
    }

    /**
     * The x-offset of the user's mouse in window coordinates
     */
    private double m_offsetX;

    /**
     * The y-offset of the user's mouse in window coordinates
     */
    private double m_offsetY;

    /**
     * The initial position of the user's mouse in game coordinates
     */
    private Point m_lastPoint;

}
