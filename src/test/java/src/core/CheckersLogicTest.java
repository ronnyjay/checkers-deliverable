package src.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.core.CheckersLogic.PlayerType;

public class CheckersLogicTest
{

    @BeforeEach
    void setUp()
    {
        game = new CheckersLogic();
    }

    @Test
    public void WhenConstructedThenInitialized()
    {
        Assertions.assertEquals(game.getPlayerX(), game.getCurrentPlayer());
        Assertions.assertEquals(game.getPlayerO(), game.getPreviousPlayer());

        Assertions.assertEquals(game.getPlayerX().getNumPieces(), 12);
        Assertions.assertEquals(game.getPlayerO().getNumPieces(), 12);

        Assertions.assertEquals(game.getPlayerX().getType(), PlayerType.HUMAN);
        Assertions.assertEquals(game.getPlayerO().getType(), PlayerType.HUMAN);

        game.setRunning(true);

        Assertions.assertTrue(game.isRunning());
    }

    @Test
    public void WhenPointValidThenPointInsideBoard()
    {
        Assertions.assertTrue(game.isPointInsideBoard(new CheckersLogic.Point(0, 1)));
        Assertions.assertTrue(game.isPointInsideBoard(new CheckersLogic.Point(7, 8)));
    }

    @Test
    public void WhenPointInvalidThenPointOutsideBoard()
    {
        // Invalid y (top, bottom)
        Assertions.assertFalse(game.isPointInsideBoard(new CheckersLogic.Point(0, 0)));
        Assertions.assertFalse(game.isPointInsideBoard(new CheckersLogic.Point(0, 9)));

        // Invalid x (left, right)
        Assertions.assertFalse(game.isPointInsideBoard(new CheckersLogic.Point(-1, 1))); // Left
        Assertions.assertFalse(game.isPointInsideBoard(new CheckersLogic.Point(8, 1))); // Right
    }

    @Test
    public void WhenPointNotOccupiedThenBoardEmpty()
    {
        Assertions.assertTrue(game.isBoardEmptyAt(new CheckersLogic.Point(1, 1)));
    }

    @Test
    public void WhenPointOccupiedThenBoardNotEmpty()
    {
        Assertions.assertFalse(game.isBoardEmptyAt(new CheckersLogic.Point(0, 1)));
    }

    @Test
    public void WhenOpponentBehindThenOpponentNotInFront()
    {
        for (int i = 0; i < 12; i++)
        {
            game.getPlayerX().getPieces()[i].set(-1, -1);
            game.getPlayerO().getPieces()[i].set(-1, -1);
        }
        game.getPlayerX().getPieces()[0].set(1, 7);
        game.getPlayerO().getPieces()[0].set(0, 0);

        Assertions.assertFalse(game.isOpponentInFront(game.getPlayerX(), game.getPlayerX().getPieces()[0]));
    }

    @Test
    public void WhenOpponentNotBehindThenOpponentInFront()
    {
        for (int i = 0; i < 12; i++)
        {
            game.getPlayerX().getPieces()[i].set(-1, -1);
            game.getPlayerO().getPieces()[i].set(-1, -1);
        }
        game.getPlayerX().getPieces()[0].set(1, 7);
        game.getPlayerO().getPieces()[0].set(2, 8);

        Assertions.assertTrue(game.isOpponentInFront(game.getPlayerX(), game.getPlayerX().getPieces()[0]));
    }

    @Test
    public void WhenInputValidThenMoveConstructed()
    {
        CheckersLogic.Move move = new CheckersLogic.Move("1a-2b");

        Assertions.assertEquals(move.src(), new CheckersLogic.Point(0, 1));
        Assertions.assertEquals(move.dest(), new CheckersLogic.Point(1, 2));
    }

    @Test
    public void WhenInputInvalidThenMoveThrows()
    {
        try
        {
            new CheckersLogic.Move("1a-2b-1a-2b");
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    @Test
    public void WhenSourceInputInvalidThenMoveThrows()
    {
        try
        {
            new CheckersLogic.Move("10-2b");
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();

    }

    @Test
    public void WhenSourceLengthInvalidThenMoveThrows()
    {
        try
        {
            new CheckersLogic.Move("1aa-2b");
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    @Test
    public void WhenDestinationInputInvalidThenMoveThrows()
    {
        try
        {
            new CheckersLogic.Move("1a-22");
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();

    }

    @Test
    public void WhenDestinationLengthInvalidThenMoveThrows()
    {
        try
        {
            new CheckersLogic.Move("1a-2bb");
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    @Test
    public void WhenMoveValidThenHandled()
    {
        try
        {
            game.handleMove(new CheckersLogic.Move("3a-4b"));
        }
        catch (Exception e)
        {
            Assertions.fail();
        }
    }

    @Test
    public void WhenMoveSourceNotValidThenNotHandled()
    {
        try
        {
            game.handleMove(new CheckersLogic.Move("3b-4c"));
        }
        catch (Exception e)
        {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void WhenMoveDestinationOutsideThenNotHandled()
    {
        try
        {
            game.handleMove(new CheckersLogic.Move("2h-3i"));
        }
        catch (Exception e)
        {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void WhenMoveDestinationOccupiedThenNotHandled()
    {
        game.getPlayerX().getPieces()[0].set(1, 4);
        try
        {
            game.handleMove(new CheckersLogic.Move("3a-4b"));
        }
        catch (Exception e)
        {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void WhenMoveNotDiagonalThenNotHandled()
    {
        try
        {
            game.handleMove(new CheckersLogic.Move("3a-4c"));
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    @Test
    public void WhenMoveDistanceNotValidThenNotHandled()
    {
        try
        {
            game.handleMove(new CheckersLogic.Move("2b-5e"));
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    @Test
    public void WhenMoveDirectionNotValidThenNotHandled()
    {
        for (int i = 0; i < 12; i++)
        {
            game.getPlayerX().getPieces()[i].set(-1, -1);
            game.getPlayerO().getPieces()[i].set(-1, -1);
        }

        game.getPlayerX().getPieces()[0].set(3, 4);
        game.getPlayerO().getPieces()[0].set(4, 6);

        CheckersLogic.Point src_x = new CheckersLogic.Point(3, 4);
        CheckersLogic.Point src_y = new CheckersLogic.Point(4, 6);

        CheckersLogic.Point dest_x = new CheckersLogic.Point(2, 3);
        CheckersLogic.Point dest_y = new CheckersLogic.Point(5, 7);

        CheckersLogic.Move move_x = new CheckersLogic.Move(src_x, dest_x);
        CheckersLogic.Move move_y = new CheckersLogic.Move(src_y, dest_y);

        try
        {
            game.handleMove(move_x);
        }
        catch (Exception ignored)
        {
            ;
        }

        game.setCurrentPlayer(CheckersLogic.PlayerSymbol.O);

        try
        {
            game.handleMove(move_y);
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    @Test
    public void WhenCaptureLeftPossibleAndNotAttemptedThenNotHandled()
    {
        for (int i = 0; i < 12; i++)
        {
            game.getPlayerX().getPieces()[i].set(-1, -1);
            game.getPlayerO().getPieces()[i].set(-1, -1);
        }

        game.getPlayerX().getPieces()[0].set(3, 4);
        game.getPlayerO().getPieces()[0].set(2, 5);

        CheckersLogic.Point src  = new CheckersLogic.Point(3, 4);
        CheckersLogic.Point dest = new CheckersLogic.Point(4, 5);

        CheckersLogic.Move move = new CheckersLogic.Move(src, dest);

        try
        {
            game.handleMove(move);
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    @Test
    public void WhenCaptureRightPossibleAndNotAttemptedThenNotHandled()
    {
        for (int i = 0; i < 12; i++)
        {
            game.getPlayerX().getPieces()[i].set(-1, -1);
            game.getPlayerO().getPieces()[i].set(-1, -1);
        }

        game.getPlayerX().getPieces()[0].set(3, 4);
        game.getPlayerO().getPieces()[0].set(4, 5);

        CheckersLogic.Point src  = new CheckersLogic.Point(3, 4);
        CheckersLogic.Point dest = new CheckersLogic.Point(2, 5);

        CheckersLogic.Move move = new CheckersLogic.Move(src, dest);

        try
        {
            game.handleMove(move);
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    @Test
    public void WhenCapturePossibleAndNotAttemptedThenNotHandled()
    {
        for (int i = 0; i < 12; i++)
        {
            game.getPlayerX().getPieces()[i].set(-1, -1);
            game.getPlayerO().getPieces()[i].set(-1, -1);
        }

        game.getPlayerX().getPieces()[0].set(1, 4);
        game.getPlayerX().getPieces()[1].set(2, 3);
        game.getPlayerO().getPieces()[0].set(2, 5);

        CheckersLogic.Point src  = new CheckersLogic.Point(2, 3);
        CheckersLogic.Point dest = new CheckersLogic.Point(3, 4);

        CheckersLogic.Move move = new CheckersLogic.Move(src, dest);

        try
        {
            game.handleMove(move);
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    @Test
    public void WhenCaptureAttemptedAndNotPossibleThenNotHandled()
    {
        for (int i = 0; i < 12; i++)
        {
            game.getPlayerX().getPieces()[i].set(-1, -1);
            game.getPlayerO().getPieces()[i].set(-1, -1);
        }

        game.getPlayerX().getPieces()[0].set(3, 4);
        game.getPlayerO().getPieces()[0].set(4, 6);

        CheckersLogic.Point src  = new CheckersLogic.Point(3, 4);
        CheckersLogic.Point dest = new CheckersLogic.Point(1, 6);

        CheckersLogic.Move move = new CheckersLogic.Move(src, dest);

        try
        {
            game.handleMove(move);
        }
        catch (Exception e)
        {
            return;
        }

        Assertions.fail();
    }

    private CheckersLogic game;
}