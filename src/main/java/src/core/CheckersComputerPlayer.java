package src.core;

import src.core.CheckersLogic.Move;
import src.core.CheckersLogic.Player;
import src.core.CheckersLogic.PlayerType;

import java.util.*;

/**
 * An AI driven CheckersComputerPlayer
 */
public class CheckersComputerPlayer extends Player
{
    /**
     * Constructs a new CheckersComputerPlayer from an already existing player
     *
     * @param player The player to construct from
     */
    public CheckersComputerPlayer(Player player)
    {
        super(player.getSymbol(), PlayerType.COMPUTER);

        setPieces(player.getPieces());
    }

    /**
     * Determines and makes a suitable move in a given checkers game
     *
     * @param game The checkers game in which the move should be made
     * @return An integer representing the result of the move
     */
    public int move(CheckersLogic game)
    {
        for (var piece : getPieces())
        {
            // @formatter:off
            if (game.canPlayerCaptureFrom(this, piece))
            {
                try
                {
                    game.handleMove(new Move(piece, getNextCaptureLeft(piece)));
                    return 0;
                }
                catch (Exception ignored)
                {
                    ;
                }

                game.handleMove(new Move(piece, getNextCaptureRight(piece)));

                return 0;
            }

            ArrayList<Move> moves = new ArrayList<>();

            if (game.canPlayerMoveFrom(this, piece))
            {

                    moves.add(new Move(piece, getNextMoveLeft(piece)));


                    moves.add(new Move(piece, getNextMoveRight(piece)));

            }

            Collections.shuffle(moves);

            for (var move : moves)
            {
                try
                {
                    game.handleMove(move);

                    return 1;
                }
                catch (Exception e)
                {
                    ;
                }
            }
            // @formatter:on
        }

        return -1;
    }
}
