package src.ui;

import src.core.CheckersComputerPlayer;
import src.core.CheckersLogic;

import java.util.Scanner;

/**
 * Terminal User-Interface for Checkers
 */
public class CheckersTextInterface
{
    /**
     * Represents a user selection
     */
    public static class Selection
    {
        /**
         * Constructs a new point from a given position.
         *
         * @param input The raw input read from System.in
         * @throws IllegalArgumentException Input is unexpected or exceeds valid length (1)
         */
        public Selection(String input) throws IllegalArgumentException
        {
            if (input.length() != 1)
            {
                throw new IllegalArgumentException("Invalid selection: input must be a single character.");
            }

            char ch = input.toUpperCase().charAt(0);

            if (ch != 'P' && ch != 'C')
            {
                throw new IllegalArgumentException("Invalid selection: expected 'P' (Player) or 'C' (Computer).");
            }

            selection = ch;
        }

        /**
         * The parsed selection
         */
        public char selection;
    }


    /**
     * Represents an ANSI color code
     */
    public enum Color
    {
        /**
         * ANSI code for resetting the output color
         */
        RESET
                {
                    @Override
                    public String toString()
                    {
                        return "\033[0m";
                    }
                },
        /**
         * ANSI code for bold red
         */
        RED
                {
                    @Override
                    public String toString()
                    {
                        return "\033[1;31m";
                    }
                },
        /**
         * ANSI code for bold green
         */
        GRN
                {
                    @Override
                    public String toString()
                    {
                        return "\033[1;32m";
                    }
                },
        /**
         * ANSI code for bold yellow
         */
        YEL
                {
                    @Override
                    public String toString()
                    {
                        return "\033[1;33m";
                    }
                },
        /**
         * ANSI code for bold white
         */
        WHT
                {
                    @Override
                    public String toString()
                    {
                        return "\033[1;97m";
                    }
                }
    }

    /**
     * Logs a message to System.Out
     *
     * @param message The message to log
     */
    public static void info(String message)
    {
        System.out.printf("%s%s%s", Color.WHT, message, Color.RESET);
    }

    /**
     * Logs a message to System.Out
     *
     * @param m The message to log
     * @param c The color of the message
     */
    public static void info(String m, Color c)
    {
        System.out.printf("%s%s%s", c, m, Color.RESET);
    }

    /**
     * Logs an error message to System.Out
     *
     * @param m The message to log
     */
    public static void err(String m)
    {
        System.out.printf("\n%s%s%s\n", Color.RED, m, Color.RESET);
    }

    /**
     * Constructs a new text interface
     *
     * @param game The game object to bind to
     */
    public CheckersTextInterface(CheckersLogic game)
    {
        this.game    = game;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Renders the current state of the game board
     */
    public void render()
    {
        final int ROWS = 8;
        final int COLS = 8;

        info("\n");

        for (int row = 0; row < ROWS; row++)
        {
            info((ROWS - row) + "|");

            for (int col = 0; col < COLS; col++)
            {
                var point = new CheckersLogic.Point(col, ROWS - row);

                // @formatter:off
                if (game.getPlayerX().hasPieceAt(point))
                {
                    System.out.print(game.getPlayerX().getSymbol().toString().toLowerCase());
                }
                else if (game.getPlayerO().hasPieceAt(point))
                {
                    System.out.print(game.getPlayerO().getSymbol().toString().toLowerCase());
                }
                else
                {
                    info("_");
                }
                // @formatter:on

                info("|");
            }

            info("\n");
        }

        info("  ");

        for (int col = 0; col < COLS; col++)
        {
            info((char) ('a' + col) + " ");
        }

        info("\n");
    }

    /**
     * Announces the start of the game
     */
    public void start()
    {
        System.out.printf("\n%sStarting game against %s. You are %s and %s is %s.\n\nBegin.%s",
                          Color.WHT, game.getPlayerO().getType().toString().toLowerCase(), game.getPlayerX(),
                          game.getPlayerO().getType(), game.getPlayerO(),
                          Color.RESET);
    }

    /**
     * Announces the end of the game
     */
    public void end()
    {

        if (game.getCurrentPlayer().getNumPieces() > game.getPreviousPlayer().getNumPieces())
        {
            info("\n" + game.getCurrentPlayer() + " has won the game!" + "\n", Color.GRN);
        }
        else if (game.getCurrentPlayer().getNumPieces() < game.getPreviousPlayer().getNumPieces())
        {
            info("\n" + game.getPreviousPlayer() + " has won the game!" + "\n", Color.GRN);
        }
        else
        {
            info("\nIt's a tie!" + "\n", Color.GRN);
        }

    }

    /**
     * Prompts the current player for a move
     */
    public void promptMove()
    {
        info("\n" + game.getCurrentPlayer()
                     + " - your turn.\nEnter the position of the piece to move and the position to move to. eg., 3a-4b:\n=> ");
    }

    /**
     * Prompts Player X to choose an opponent type
     */
    public void promptSelection()
    {
        info("\nChoose an opponent. Enter 'P' to play against another player. Enter 'C' to play against the computer:\n=> ");
    }

    /**
     * Polls the current player's move
     *
     * @return Current player's move
     */
    public CheckersLogic.Move pollMove()
    {
        return new CheckersLogic.Move(scanner.nextLine());
    }

    /**
     * Polls Player X's opponent type selection
     *
     * @return Player X's selection
     */
    public Selection pollSelection()
    {
        return new Selection(scanner.nextLine());
    }

    /**
     * Initializes the checker's logic using the terminal user-interface
     *
     * @param args The command-line arguments passed to the program
     */
    public static void main(String[] args)
    {
        CheckersLogic         game = new CheckersLogic();
        CheckersTextInterface ui   = new CheckersTextInterface(game);

        ui.render();

        while (true)
        {
            ui.promptSelection();

            // @formatter:off
            try
            {
                if (ui.pollSelection().selection == 'C')
                {
                    game.setOpponentComputer();
                }

                break;
            }
            catch (IllegalArgumentException e)
            {
                CheckersTextInterface.err(e.getMessage());
            }
            // @formatter:on
        }

        ui.start();

        game.setRunning(true);

        // @formatter:off
        while (game.isRunning())
        {
            if (game.getCurrentPlayer().getType() == CheckersLogic.PlayerType.HUMAN)
            {

                ui.promptMove();

                try
                {
                    game.handleMove(ui.pollMove());
                }
                catch (IllegalArgumentException e)
                {
                    CheckersTextInterface.err(e.getMessage());
                }
            }
            else
            {
                ((CheckersComputerPlayer) game.getCurrentPlayer()).move(game);
            }
            // @formatter:on

            ui.render();
        }

        ui.end();
    }


    private final CheckersLogic game;
    private final Scanner       scanner;
}
