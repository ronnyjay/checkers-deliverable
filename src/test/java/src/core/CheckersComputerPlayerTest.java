package src.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckersComputerPlayerTest
{
    @Before
    public void setup()
    {
        game = new CheckersLogic();

        for (int i = 0; i < 12; i++)
        {
            game.getPlayerX().getPieces()[i].x = -1;
            game.getPlayerX().getPieces()[i].y = -1;

            game.getPlayerO().getPieces()[i].x = -1;
            game.getPlayerO().getPieces()[i].y = -1;
        }

        game.getPlayerX().getPieces()[0].x = 3;
        game.getPlayerX().getPieces()[0].y = 4;

        game.getPlayerX().getPieces()[1].x = 3;
        game.getPlayerX().getPieces()[1].y = 2;

        game.getPlayerX().getPieces()[2].x = 2;
        game.getPlayerX().getPieces()[2].y = 1;

        game.getPlayerX().getPieces()[3].x = 0;
        game.getPlayerX().getPieces()[3].y = 1;

        game.getPlayerO().getPieces()[0].x = 4;
        game.getPlayerO().getPieces()[0].y = 5;

        game.setOpponentComputer();
    }

    @Test
    public void WhenCapturePossibleThenCaptureAttempted()
    {
        // Capture left
        game.setCurrentPlayer(CheckersLogic.PlayerSymbol.O);
        assertEquals(((CheckersComputerPlayer) game.getPlayerO()).move(game), 0);

        // Capture right
        game.setCurrentPlayer(CheckersLogic.PlayerSymbol.O);
        assertEquals(((CheckersComputerPlayer) game.getPlayerO()).move(game), 0);
    }

    @Test
    public void WhenCaptureNotPossibleThenMoveAttempted()
    {
        game.setCurrentPlayer(CheckersLogic.PlayerSymbol.O);

        game.getPlayerO().getPieces()[0].x = 3;
        game.getPlayerO().getPieces()[0].y = 4;

        assertEquals(((CheckersComputerPlayer) game.getPlayerO()).move(game), 1);
    }

    @Test
    public void WhenPiecesMissingThenMoveNotAttempted()
    {
        game.setCurrentPlayer(CheckersLogic.PlayerSymbol.O);

        for (int i = 0; i < 12; i++)
        {
            game.getPlayerO().getPieces()[i].set(-1, -1);
        }

        assertEquals(((CheckersComputerPlayer) game.getPlayerO()).move(game), -1);
    }

    private CheckersLogic game;
}
