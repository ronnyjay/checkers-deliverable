package src.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import src.core.CheckersLogic;
import src.core.CheckersLogic.PlayerType;

import java.io.IOException;

/**
 * A gui for checkers
 */
public class CheckersGUI extends Application
{
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(CheckersGUI.class.getResource("scene.fxml"));
        Scene      scene      = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);

        controller = fxmlLoader.getController();

        // @formatter:off
        PIECES[0]  = controller.X_0;
        PIECES[12] = controller.Y_0;

        PIECES[1]  = controller.X_1;
        PIECES[13] = controller.Y_1;

        PIECES[2]  = controller.X_2;
        PIECES[14] = controller.Y_2;

        PIECES[3]  = controller.X_3;
        PIECES[15] = controller.Y_3;

        PIECES[4]  = controller.X_4;
        PIECES[16] = controller.Y_4;

        PIECES[5]  = controller.X_5;
        PIECES[17] = controller.Y_5;

        PIECES[6]  = controller.X_6;
        PIECES[18] = controller.Y_6;

        PIECES[7]  = controller.X_7;
        PIECES[19] = controller.Y_7;

        PIECES[8]  = controller.X_8;
        PIECES[20] = controller.Y_8;

        PIECES[9]  = controller.X_9;
        PIECES[21] = controller.Y_9;

        PIECES[10] = controller.X_10;
        PIECES[22] = controller.Y_10;

        PIECES[11] = controller.X_11;
        PIECES[23] = controller.Y_11;
        // @formatter:off
        
        update();

        AnimationTimer frameTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                render();
            }
        };
        frameTimer.start();

        stage.setTitle("Checkers");

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    protected static void update()
    {
        var PiecesX = GAME.getPlayerX().getPieces();
        var piecesO = GAME.getPlayerO().getPieces();

        for (int i = 0; i < 12; i++)
        {
            var posX = PiecesX[i];
            var posO = piecesO[i];

            // Piece is captured, don't display
            if (posX.equals(new CheckersLogic.Point(-1, -1)))
            {
                PIECES[i].setVisible(false);
            }

            // Piece is captured, dont display
            if (posO.equals(new CheckersLogic.Point(-1, -1)))
            {
                PIECES[12 + i].setVisible(false);
            }

            // Re-calculate window coords. for visual piece
            var posWorldX = CheckersGUI.pointToWorldSpace(posX);
            var posWorldO = CheckersGUI.pointToWorldSpace(posO);

            PIECES[i].setLayoutX(posWorldX.x);
            PIECES[i].setLayoutY(posWorldX.y);

            PIECES[12 + i].setLayoutX(posWorldO.x);
            PIECES[12 + i].setLayoutY(posWorldO.y);

            // Apply effects to the pieces that are able to move
            //
            // Effects are shown only for the current player

            DropShadow glowX = new DropShadow();
            glowX.setColor((Color) Paint.valueOf("rgba(200, 150, 0, 0.8)"));
            glowX.setRadius(20);
            glowX.setSpread(0.6);

            DropShadow glowO = new DropShadow();
            glowO.setColor((Color) Paint.valueOf("rgba(200, 0, 0, 0.8)"));
            glowO.setRadius(20);
            glowO.setSpread(0.6);

            if (!GAME.isRunning())
            {
                continue;
            }

            // @formatter:off
            if (GAME.canPlayerCaptureFrom(GAME.getCurrentPlayer(), posX))
            {
                PIECES[i].setStroke(Paint.valueOf("green"));
                PIECES[i].setStrokeWidth(5.0);
                PIECES[i].setEffect(glowX);
            }
            else if (!GAME.canPlayerCapture(GAME.getCurrentPlayer()))
            {
                if (GAME.canPlayerMoveFrom(GAME.getCurrentPlayer(), posX))
                {
                    PIECES[i].setStroke(Paint.valueOf("green"));
                    PIECES[i].setStrokeWidth(5.0);
                    PIECES[i].setEffect(glowX);
                }
                else 
                {
                    PIECES[i].setStrokeWidth(0.0);
                    PIECES[i].setEffect(null);
                }
            }
            else {
                PIECES[i].setStrokeWidth(0.0);
                PIECES[i].setEffect(null);
            }

            if (GAME.canPlayerCaptureFrom(GAME.getCurrentPlayer(), posO))
            {
                PIECES[12 + i].setStroke(Paint.valueOf("red"));
                PIECES[12 + i].setStrokeWidth(5.0);
                PIECES[12 + i].setEffect(glowO);
            }
            else if (!GAME.canPlayerCapture(GAME.getCurrentPlayer()))
            {
                if (GAME.canPlayerMoveFrom(GAME.getCurrentPlayer(), posO))
                {
                    PIECES[12 + i].setStroke(Paint.valueOf("rgba(150, 0, 0, 1.0)"));
                    PIECES[12 + i].setStrokeWidth(5.0);
                    PIECES[12 + i].setEffect(glowO);
                }
                else 
                {
                    PIECES[12 + i].setStrokeWidth(0.0);
                    PIECES[12 + i].setEffect(null);
                }
            }
            else {
                PIECES[12 + i].setStrokeWidth(0.0);
                PIECES[12 + i].setEffect(null);
            }
            // @formatter:on

            if (GAME.getPreviousPlayer().hasPieceAt(posX))
            {
                PIECES[i].setStrokeWidth(0.0);
                PIECES[i].setEffect(null);
            }

            if (GAME.getPreviousPlayer().hasPieceAt(posO))
            {
                PIECES[12 + i].setStrokeWidth(0.0);
                PIECES[12 + i].setEffect(null);
            }

            if (TURN_OVER)
            {

                PIECES[i].setStrokeWidth(0.0);
                PIECES[i].setEffect(null);

                PIECES[12 + i].setStrokeWidth(0.0);
                PIECES[12 + i].setEffect(null);
            }
        }
    }

    protected static void reset()
    {
        // GAME.reset();
        GAME = new CheckersLogic();

        for (int i = 0; i < 24; i++)
        {
            PIECES[i].setVisible(true);
        }

        TURN_OVER = false;

        update();
    }

    protected void render()
    {
        // Always turn this off
        controller.TURN_TEXT.setVisible(false);

        // @formatter:off
        if (GAME.isRunning())
        {
            controller.WIN_TEXT.setVisible(false);
            controller.TIE_TEXT.setVisible(false);

            // Hide until next player begins turn
            if (TURN_OVER)
            {
                return;
            }

            // Computer is too fast to display, only show turns for opponent types
            if (GAME.getCurrentPlayer().getType() != PlayerType.COMPUTER)
            {
                controller.TURN_TEXT.setText(GAME.getCurrentPlayer().toString().toUpperCase() + " - YOUR TURN");
                controller.TURN_TEXT.setVisible(true);
            }
        }
        else
        {
            // Player X wins
            if (GAME.getPlayerX().getNumPieces() > GAME.getPlayerO().getNumPieces())
            {
                 if (controller.WIN_TEXT.getStyle().contains("rgba(255, 0, 0, 1.0)"))
                {
                    controller.WIN_TEXT.setStyle(controller.WIN_TEXT.getStyle().replace("rgba(255, 0, 0, 1.0)", "rgba(0, 255, 10, 1.0)"));
                }

                controller.WIN_TEXT.setText(GAME.getPlayerX().toString().toUpperCase() + " WINS!");
                controller.WIN_TEXT.setVisible(true);
            }
            // Player O wins
            else if (GAME.getPlayerX().getNumPieces() < GAME.getPlayerO().getNumPieces())
            {
                if (controller.WIN_TEXT.getStyle().contains("rgba(0, 255, 10, 1.0)"))
                {
                    controller.WIN_TEXT.setStyle(controller.WIN_TEXT.getStyle().replace("rgba(0, 255, 10, 1.0)", "rgba(255, 0, 0, 1.0)"));
                }

                controller.WIN_TEXT.setText(GAME.getPlayerO().toString().toUpperCase() + " WINS!");
                controller.WIN_TEXT.setVisible(true);
            }
            // Tie
            else 
            {   
                // Prevents the GUI from assuming a tie when game has just begin
                if (GAME.getCurrentPlayer().getNumPieces() == 12 && GAME.getPreviousPlayer().getNumPieces() == 12)
                {
                    return;
                }

                controller.TIE_TEXT.setVisible(true);
            }

            // Game finished, reset all effects
            for (int i = 0; i < 24; i++)
            {

                PIECES[i].setEffect(null);
                PIECES[i].setStrokeWidth(0.0);
            }

            controller.RESET_BTN.setVisible(true);
            controller.RESET_BTN.setDisable(false);
        }
        // @formatter:on
    }

    /**
     * Converts a window coordinate to a game coordinate
     *
     * @param point The point to convert
     * @return The converted point as a game coordinate
     */
    protected static CheckersLogic.Point pointFromWorldSpace(CheckersLogic.Point point)
    {
        var x = ((point.x - BOARD_START_X) / CELL_WIDTH) + 1;
        var y = ((point.y - BOARD_START_Y) / CELL_HEIGHT) + 1;

        return new CheckersLogic.Point(x - 1, (8 - y) + 1);
    }

    /**
     * Converts a game coordinate to a window coordinate
     *
     * @param point The point to convert
     * @return The converted point as a window coordinate
     */
    protected static CheckersLogic.Point pointToWorldSpace(CheckersLogic.Point point)
    {
        var x = (BOARD_START_X + (point.x) * CELL_WIDTH) + (CELL_WIDTH / 2);
        var y = (BOARD_START_Y + (8 - point.y) * CELL_HEIGHT) + (CELL_HEIGHT / 2);

        return new CheckersLogic.Point(x, y);
    }

    private CheckersController controller;

    /**
     * The GUI's game object
     */
    protected static CheckersLogic GAME = new CheckersLogic();

    /**
     * The GUI's representation of both players' game pieces
     */
    private static final Circle[] PIECES = new Circle[24];

    /**
     * The GUI's flag for when a player has finished a move
     */
    protected static boolean TURN_OVER = false;

    /**
     * The width (in pixels) of a cell making up the game board
     */
    protected static final int CELL_WIDTH = 74;

    /**
     * The height (in pixels) of a cell making up the game board
     */
    protected static final int CELL_HEIGHT = 68;

    /**
     * The width (in pixels) of the window
     */
    protected static final int WINDOW_WIDTH = 800;

    /**
     * The height (in pixels) of the window
     */
    protected static final int WINDOW_HEIGHT = 800;

    /**
     * The width (in pixels) of the game board
     */
    protected static final int BOARD_WIDTH = CELL_WIDTH * 8;

    /**
     * The height (in pixels) of the game board
     */
    protected static final int BOARD_HEIGHT = CELL_HEIGHT * 8;

    /**
     * The x-coordinate of the leftmost side of the game board
     */
    protected static final int BOARD_START_X = (WINDOW_WIDTH - BOARD_WIDTH) / 2;

    /**
     * The y-coordinate of the topmost side of the game board
     */
    protected static final int BOARD_START_Y = 45;
}
