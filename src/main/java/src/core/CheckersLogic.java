package src.core;

/**
 * Logic for one to many checkers matches
 */
public class CheckersLogic
{
    /**
     * Represents any point with an x, y component
     */
    public static class Point
    {
        /**
         * Default constructor
         */
        public Point()
        {
        }

        /**
         * Constructs a new point from a given point
         *
         * @param other The point to copy
         */
        public Point(Point other)
        {
            if (this != other)
            {
                x = other.x;
                y = other.y;
            }
        }

        /**
         * Constructs a new point from a given position
         *
         * @param x The x-coordinate of the point
         * @param y The y-coordinate of the point
         */
        public Point(int x, int y)
        {
            set(x, y);
        }

        /**
         * Updates the x and y coordinates of the point
         *
         * @param x The new x-coordinate
         * @param y The new y-coordinate
         */
        public void set(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        /**
         * The x-coordinate of the point
         */
        public int x = -1;

        /**
         * The y-coordinate of the point
         */
        public int y = -1;

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }

            if (obj == null || getClass() != obj.getClass())
            {
                return false;
            }

            Point point = (Point) obj;

            return (x == point.x) && (y == point.y);
        }

        @Override
        public String toString()
        {
            return String.format("(%c, %d)", x + 'a', y);
        }

        /**
         * Constructs and returns a new point from a string representation
         *
         * @param string The string representation of the point
         * @return The new point
         */
        public static Point fromString(String string)
        {
            return new Point((short) (string.charAt(1) - 'a'), (short) (string.charAt(0) - '0'));
        }
    }

    /**
     * Represents an in-game move
     */
    public static class Move
    {
        /**
         * Default constructor
         *
         * @param string The string representation of the move
         * @throws IllegalArgumentException The string representation is invalid
         */
        public Move(String string) throws IllegalArgumentException
        {
            var point_strings = string.split("-");

            if (point_strings.length != 2)
            {
                throw new IllegalArgumentException("expected <src>-<dest>");
            }

            var src  = point_strings[0];
            var dest = point_strings[1];

            if (src.length() != 2)
            {
                throw new IllegalArgumentException("<src> exceeds the valid length");
            }

            if (!Character.isDigit(src.charAt(0)) || !Character.isLetter(src.charAt(1)))
            {
                throw new IllegalArgumentException("<src> has invalid format");
            }

            if (dest.length() != 2)
            {
                throw new IllegalArgumentException("<dest> exceeds the valid length");
            }

            if (!Character.isDigit(dest.charAt(0)) || !Character.isLetter(dest.charAt(1)))
            {
                throw new IllegalArgumentException("<dest> has invalid format");
            }

            this.src  = Point.fromString(src);
            this.dest = Point.fromString(dest);
        }

        /**
         * Constructs a new move from two points
         *
         * @param src  The point to move from
         * @param dest The point to move to
         */
        public Move(Point src, Point dest)
        {
            this.src  = src;
            this.dest = dest;
        }

        /**
         * Utility function to retrieve the move's initial point
         *
         * @return The move's source
         */
        public Point src()
        {
            return this.src;
        }

        /**
         * Utility function to retrieve the move's final point
         *
         * @return The move's destination
         */
        public Point dest()
        {
            return this.dest;
        }

        private final Point src;
        private final Point dest;
    }

    /**
     * Represents a player's type
     */
    public enum PlayerType
    {
        /**
         * Unassigned
         */
        NONE
                {
                    @Override
                    public String toString()
                    {
                        return "None";
                    }
                },
        /**
         * Human opponent
         */
        HUMAN
                {
                    @Override
                    public String toString()
                    {
                        return "Human";
                    }
                },
        /**
         * Computer opponent
         */
        COMPUTER
                {
                    @Override
                    public String toString()
                    {
                        return "Computer";
                    }
                }
    }

    /**
     * Represents a player's symbol
     */
    public enum PlayerSymbol
    {
        /**
         * Unassigned
         */
        NONE
                {
                    @Override
                    public String toString()
                    {
                        return "\0";
                    }
                },
        /**
         * X symbol
         */
        X
                {
                    @Override
                    public String toString()
                    {
                        return "X";
                    }
                },
        /**
         * O Symbol
         */
        O
                {
                    @Override
                    public String toString()
                    {
                        return "O";
                    }
                };

    }

    /**
     * Represents a player
     */
    public static class Player
    {
        /**
         * Default constructor
         *
         * @param symbol The desired symbol
         * @param type   The desired type
         */
        public Player(PlayerSymbol symbol, PlayerType type)
        {
            pieces = new Point[12];

            for (int i = 0; i < 12; i++)
            {
                pieces[i] = new Point();
            }

            this.type   = type;
            this.symbol = symbol;
        }

        /**
         * Utility function to retrieve the player's type
         *
         * @return The player's type
         */
        public PlayerType getType()
        {
            return type;
        }

        /**
         * Utility function to set the player's type
         *
         * @param type The desired type
         */
        public void setType(PlayerType type)
        {
            this.type = type;
        }

        /**
         * Utility function to retrieve the player's symbol
         *
         * @return The player's symbol
         */
        public PlayerSymbol getSymbol()
        {
            return symbol;
        }

        /**
         * Utility function to set the player's symbol
         *
         * @param symbol The desired symbol
         */
        public void setSymbol(PlayerSymbol symbol)
        {
            this.symbol = symbol;
        }

        /**
         * Utility function to set the player's pieces
         *
         * @param pieces The desired array of pieces
         */
        public void setPieces(Point[] pieces)
        {
            this.pieces = pieces;
        }

        /**
         * Utility function to retrieve the player's pieces
         *
         * @return The player's pieces
         */
        public Point[] getPieces()
        {
            return pieces;
        }

        /**
         * Utility function to retrieve the player's next left normal move from a given
         * point
         *
         * @param point The point to compare to
         * @return The player's next left normal move
         */
        public Point getNextMoveLeft(Point point)
        {
            short direction = 1;

            if (symbol == PlayerSymbol.O)
            {
                direction = (short) -direction;
            }

            return new Point(point.x - 1, point.y + direction);
        }

        /**
         * Utility function to retrieve the player's next right normal move from a
         * given point
         *
         * @param point The point to compare to
         * @return The player's next right normal mvoe
         */
        public Point getNextMoveRight(Point point)
        {
            int direction = 1;

            if (symbol == PlayerSymbol.O)
            {
                direction = -direction;
            }

            return new Point(point.x + 1, point.y + direction);
        }

        /**
         * Utility function to retrieve the player's next left capture move from a given
         * point
         *
         * @param point The point to compare to
         * @return The player's next left capture move
         */
        public Point getNextCaptureLeft(Point point)
        {
            int direction = 1;

            if (symbol == PlayerSymbol.O)
            {
                direction = -direction;
            }

            return new Point(point.x - 2, point.y + (2 * direction));
        }

        /**
         * Utility function to retrieve the player's next right capture move from a
         * given point
         *
         * @param point The point to compare to
         * @return The player's next right capture mvoe
         */
        public Point getNextCaptureRight(Point point)
        {
            int direction = 1;

            if (symbol == PlayerSymbol.O)
            {
                direction = -direction;
            }

            return new Point(point.x + 2, point.y + (2 * direction));
        }

        /**
         * Utility function to retrieve the player's piece at a given point
         *
         * @param point The point to compare to
         * @return The piece at the given point if found, null otherwise
         */
        public Point getPieceAt(Point point)
        {
            for (var piece : pieces)
            {
                if (piece == null)
                {
                    continue;
                }

                if (piece.equals(point))
                {
                    return piece;
                }
            }

            return null;
        }

        /**
         * Determines if the player has a piece at a given point
         *
         * @param point The point to compare to
         * @return True if player has piece, false otherwise
         */
        public boolean hasPieceAt(Point point)
        {
            for (var piece : pieces)
            {
                if (piece == null)
                {
                    continue;
                }

                if (piece.equals(point))
                {
                    return true;
                }
            }

            return false;
        }

        /**
         * Utility function to retrieve the number of active pieces owned by the player
         *
         * @return The number of active pieces owned by the player
         */
        public int getNumPieces()
        {
            int count = 0;

            for (var piece : pieces)
            {
                if (piece.equals(new Point(-1, -1)))
                {
                    continue;
                }
                count++;
            }

            return count;
        }

        @Override
        public String toString()
        {
            return "Player " + symbol;
        }

        private PlayerType   type;
        private PlayerSymbol symbol;
        private Point[]      pieces;
    }

    /**
     * Default constructor
     */
    public CheckersLogic()
    {
        playerX = new Player(PlayerSymbol.X, PlayerType.HUMAN);
        playerO = new Player(PlayerSymbol.O, PlayerType.HUMAN);

        currentPlayer  = playerX;
        previousPlayer = playerO;

        playerX.getPieces()[0].set(0, 1);
        playerO.getPieces()[0].set(1, 8);

        playerX.getPieces()[1].set(2, 1);
        playerO.getPieces()[1].set(3, 8);

        playerX.getPieces()[2].set(4, 1);
        playerO.getPieces()[2].set(5, 8);

        playerX.getPieces()[3].set(6, 1);
        playerO.getPieces()[3].set(7, 8);

        playerX.getPieces()[4].set(1, 2);
        playerO.getPieces()[4].set(0, 7);

        playerX.getPieces()[5].set(3, 2);
        playerO.getPieces()[5].set(2, 7);

        playerX.getPieces()[6].set(5, 2);
        playerO.getPieces()[6].set(4, 7);

        playerX.getPieces()[7].set(7, 2);
        playerO.getPieces()[7].set(6, 7);

        playerX.getPieces()[8].set(0, 3);
        playerO.getPieces()[8].set(1, 6);

        playerX.getPieces()[9].set(2, 3);
        playerO.getPieces()[9].set(3, 6);

        playerX.getPieces()[10].set(4, 3);
        playerO.getPieces()[10].set(5, 6);

        playerX.getPieces()[11].set(6, 3);
        playerO.getPieces()[11].set(7, 6);

        running = false;
    }

    /**
     * Utility function to set the current player
     *
     * @param player The desired player
     */
    public void setCurrentPlayer(PlayerSymbol player)
    {
        switch (player)
        {
            case X:
                currentPlayer = playerX;
                previousPlayer = playerO;
                break;
            case O:
                currentPlayer = playerO;
                previousPlayer = playerX;
                break;
            default:
                break;
        }
    }

    /**
     * Utility function to set Player X's opponent to computer
     */
    public void setOpponentComputer()
    {
        playerO        = new CheckersComputerPlayer(playerO);
        previousPlayer = playerO;
    }

    /**
     * Utility function to the running state of the game
     *
     * @param running The desired state
     */
    public void setRunning(boolean running)
    {
        this.running = running;
    }

    /**
     * Determines if the game can continue
     *
     * @return True if running, false otherwise
     */
    public boolean isRunning()
    {
        return (canPlayerMove(currentPlayer) || canPlayerCapture(currentPlayer)) && running;
    }

    /**
     * Determines if a point is inside of the playing area
     *
     * @param point The point to compare to
     * @return True if point inside, false otherwise
     */
    public boolean isPointInsideBoard(Point point)
    {
        return (point.x >= 0 && point.x <= 7) && (point.y >= 1 && point.y <= 8);
    }

    /**
     * Determines if the board is empty at at a given point
     *
     * @param point The point to compare to
     * @return True if board empty, false otherwise
     */
    public boolean isBoardEmptyAt(Point point)
    {
        return !playerX.hasPieceAt(point) && !playerO.hasPieceAt(point);
    }

    /**
     * Recursively determines whether the opponent has any reachable pieces by
     * exploring all possible moves from a given point and its subsuquent moves
     *
     * @param player The player
     * @param point  The point to compare to
     * @return True if an opponent's piece exists inside of the potential move path,
     * false otherwise
     */
    public boolean isOpponentInFront(Player player, Point point)
    {
        var nextLeft  = player.getNextMoveLeft(point);
        var nextRight = player.getNextMoveRight(point);

        if (isPointInsideBoard(nextLeft) || isPointInsideBoard(nextRight))
        {
            switch (player.getSymbol())
            {
                case X:
                    if (playerO.hasPieceAt(nextLeft) || playerO.hasPieceAt(nextRight))
                    {
                        return true;
                    }
                    break;
                case O:
                    if (playerX.hasPieceAt(nextLeft) || playerX.hasPieceAt(nextRight))
                    {
                        return true;
                    }
                    break;
                default:
                    break;
            }

            return isOpponentInFront(player, nextLeft) || isOpponentInFront(player, nextRight);
        }

        return false;
    }

    /**
     * Determines if a player can make a normal move from any owned point
     *
     * @param player The player
     * @return True if the player can make a normal move, false otherwise
     */
    public boolean canPlayerMove(Player player)
    {
        for (var piece : player.getPieces())
        {
            if (piece == null)
            {
                continue;
            }

            if (canPlayerMoveFrom(player, piece) && isOpponentInFront(player, piece))
            {
                return true;
            }

        }

        return false;
    }

    /**
     * Determines if a player can make a normal move from a given point
     *
     * @param player The player
     * @param point  The poitn to compare to
     * @return True if the player can make a normal move, false otherwise
     */
    public boolean canPlayerMoveFrom(Player player, Point point)
    {
        int direction = 1;

        if (player.getSymbol() == PlayerSymbol.O)
        {
            direction = -direction;
        }

        var nextLeft  = player.getNextMoveLeft(point);
        var nextRight = player.getNextMoveRight(point);

        if (isBoardEmptyAt(nextLeft) && isPointInsideBoard(nextLeft))
        {
            return true;
        }

        if (isBoardEmptyAt(nextRight) && isPointInsideBoard(nextRight))
        {
            return true;
        }

        return false;
    }

    /**
     * Determines if a player can make a capture move from any owned point
     *
     * @param player The player
     * @return True if the player can make a capture move, false otherwise
     */
    public boolean canPlayerCapture(Player player)
    {
        for (var piece : player.getPieces())
        {
            if (piece == null)
            {
                continue;
            }

            if (canPlayerCaptureFrom(player, piece))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines if a player can make a capture move from a given point
     *
     * @param player The player
     * @param point  The point to compare to
     * @return True if player can make a capture move, false otherwise
     */
    public boolean canPlayerCaptureFrom(Player player, Point point)
    {
        int direction = 1;

        if (player.getSymbol() == PlayerSymbol.O)
        {
            direction = -direction;
        }

        var nextLeft         = player.getNextMoveLeft(point);
        var nextRight        = player.getNextMoveRight(point);
        var nextCaptureLeft  = player.getNextCaptureLeft(point);
        var nextCaptureRight = player.getNextCaptureRight(point);

        if (isBoardEmptyAt(nextCaptureLeft) && isPointInsideBoard(nextCaptureLeft)
                && previousPlayer.hasPieceAt(nextLeft))
        {
            return true;
        }

        if (isBoardEmptyAt(nextCaptureRight) && isPointInsideBoard(nextCaptureRight)
                && previousPlayer.hasPieceAt(nextRight))
        {
            return true;
        }

        return false;
    }

    /**
     * Handles an in-game move attempt
     */
    public void handleMove(Move move) throws IllegalArgumentException
    {
        if (!currentPlayer.hasPieceAt(move.src))
        {
            throw new IllegalArgumentException();
        }

        if (!isPointInsideBoard(move.dest))
        {
            throw new IllegalArgumentException();
        }

        if (!isBoardEmptyAt(move.dest))
        {
            throw new IllegalArgumentException();
        }

        int dx = move.dest.x - move.src.x;
        int dy = move.dest.y - move.src.y;

        if (Math.abs(dx) != Math.abs(dy))
        {
            throw new IllegalArgumentException();
        }

        if (Math.abs(dx) != 1 && Math.abs(dx) != 2)
        {
            throw new IllegalArgumentException();
        }

        switch (currentPlayer.getSymbol())
        {
            case X:
                if (dy < 0)
                {
                    throw new IllegalArgumentException();
                }
                break;
            case O:
                if (dy > 0)
                {
                    throw new IllegalArgumentException();
                }
                break;
            default:
                break;
        }

        var nextLeft         = currentPlayer.getNextMoveLeft(move.src);
        var nextRight        = currentPlayer.getNextMoveRight(move.src);
        var nextCaptureLeft  = currentPlayer.getNextCaptureLeft(move.src);
        var nextCaptureRight = currentPlayer.getNextCaptureRight(move.src);

        // @formatter:off
        if (canPlayerCapture(currentPlayer))
        {
            if (!canPlayerCaptureFrom(currentPlayer, move.src))
            {

                throw new IllegalArgumentException("Invalid move a: capturing is mandatory when possible");
            }

            boolean canCaptureLeft = previousPlayer.hasPieceAt(nextLeft) && isBoardEmptyAt(nextCaptureLeft) && isPointInsideBoard(nextCaptureLeft);
            boolean canCaptureRight = previousPlayer.hasPieceAt(nextRight) && isBoardEmptyAt(nextCaptureRight) && isPointInsideBoard(nextCaptureRight);

            boolean isCapturingLeft = move.dest.equals(nextCaptureLeft);
            boolean isCapturingRight = move.dest.equals(nextCaptureRight);

            if (canCaptureLeft && !isCapturingLeft && !canCaptureRight)
            {
       
                throw new IllegalArgumentException("Invalid move: capturing is mandatory when possible");
            }
             if (canCaptureRight && !isCapturingRight && !canCaptureLeft)
            {
          
                throw new IllegalArgumentException("Invalid move: capturing is mandatory when possible");
            }
        } 
        else
        {
            if (Math.abs(dx) == 2)
            {
                throw new IllegalArgumentException("Invalid move: unable to capture");
            }
        }
        // @formatter:on

        if (Math.abs(dx) == 2)
        {
            int nextX = move.src.x + (dx / 2);
            int nextY = move.src.y + (dy / 2);

            previousPlayer.getPieceAt(new Point(nextX, nextY)).set(-1, -1);
        }

        currentPlayer.getPieceAt(move.src).set(move.dest.x, move.dest.y);

        Player temp = currentPlayer;
        currentPlayer  = previousPlayer;
        previousPlayer = temp;

    }

    /**
     * Utility function to retrieve the game's player X
     *
     * @return The game's Player X
     */
    public Player getPlayerX()
    {
        return playerX;
    }

    /**
     * Utility function to retrieve the game's player O
     *
     * @return The game's Player O
     */
    public Player getPlayerO()
    {
        return playerO;
    }

    /**
     * Utility function to retrieve the game's current player
     *
     * @return The game's current player
     */
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    /**
     * Utility function to retrieve the game's previous player
     *
     * @return The game's previous player
     */
    public Player getPreviousPlayer()
    {
        return previousPlayer;
    }

    private boolean running;

    private Player playerX;
    private Player playerO;

    private Player currentPlayer;
    private Player previousPlayer;

}
