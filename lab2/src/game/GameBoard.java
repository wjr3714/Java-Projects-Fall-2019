/*
 * file: GameBoard.java
 * GameBoard is a provided part of the DotsAndBoxes program solution.
 *
 * CS.RIT.EDU
 * A student should NOT make any changes to this code.
 */

package game;

/**
 * This class represents and plays the Dots And Boxes game.
 *
 * It is called on by DotsAndBoxes and it uses the following classes:
 * @see Player
 * @see Dot
 * @see Box
 * @see Lines
 *
 * @author Sean Strout @ RIT CS
 */
public class GameBoard {
    /** number of rows */
    private int rows;
    /** number of columns */
    private int columns;
    /** red's score */
    private int redScore;
    /** blue's score */
    private int blueScore;
    /** the current player */
    private Player player;
    /** the grid of dots */
    private Dot[][] dots;
    /** the collection of lines */
    private Lines lines;
    /** the grid of boxes defined by their upper left coordinate */
    private Box[][] boxes;
    /** the move counter */
    private int moves;

    /**
     * Create the game board.  The red player goes first.
     *
     * @param rows number of rows
     * @param columns number of columns
     */
    public GameBoard(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        // no score, red plays first, and no moves have been made
        this.redScore = this.blueScore = 0;
        this.player = Player.RED;
        this.moves = 0;

        // built the dot grid given the # of dots = m * n
        final int m = this.rows + 1;
        final int n = this.columns + 1;

        this.dots = new Dot[m][n];
        for (int row=0; row<m; ++row) {
            for (int column=0; column<n; ++column) {
                this.dots[row][column] = new Dot(row, column);
            }
        }

        // build the lines given the dot grid
        this.lines = new Lines(rows, columns, this.dots);

        // build box grid given the lines
        this.boxes = new Box[this.rows][this.columns];
        for (int row=0; row<this.rows; ++row) {
            for (int column=0; column<this.columns; ++column) {
                this.boxes[row][column] = new Box(row, column, this.lines);
            }
        }
    }

    /**
     * Is the game over?  This happens when the number of lines claimed
     * equals the number of moves made.
     *
     * @return whether the game is over, man!
     */
    public boolean gameOver() { return this.moves == this.lines.size(); }

    /**
     * Whose turn is it anyway?
     *
     * @return the player who has the current turn
     */
    public Player whoseTurn() {  return this.player; }

    /**
     * Are the coordinates for this line valid or not?  To be valid it
     * must be a line that exists and is unclaimed.
     *
     * @param row1 first row
     * @param column1 first column
     * @param row2 second row
     * @param column2 second column
     * @return whether the line is valid or not
     */
    public boolean isLineValid(int row1, int column1, int row2, int column2) {
        Line line = this.lines.getLine(row1, column1, row2, column2);
        return line != null && !line.hasOwner(); // <3 IntelliJ for simplifying
    }

    /**
     * Make a move in the game given a valid line to claim.  A move is made by
     * specifying an unclaimed line to be owned by the current player.  If the
     * move claims a box, that player gets to go again, otherwise the next turn
     * is swapped to the other player.
     *
     * @param row1 first row
     * @param column1 first column
     * @param row2 second row
     * @param column2 second column
     * @rit.pre the coordinates for the line have already been checked
     * for validity and can be claimed
     */
    public void makeMove(int row1, int column1, int row2, int column2) {
        // claim the line for the owner
        Line line = this.lines.getLine(row1, column1, row2, column2);
        line.claim(this.player);

        // check how many boxes, if any, were claimed by claiming this line
        int numBoxesClaimed = 0;
        for (Box box : line.getBoxes()) {
            if (box.getOwner() != Player.NONE) {
                ++numBoxesClaimed;
            }
        }

        // update score
        if (this.player == Player.RED) {
            this.redScore += numBoxesClaimed;
        } else {
            this.blueScore += numBoxesClaimed;
        }

        // swap players if a box was not claimed
        if (numBoxesClaimed == 0) {
            this.player = this.player == Player.RED ? Player.BLUE : Player.RED;
        }

        // always increment the move counter
        ++this.moves;
    }

    /**
     * Return a string representation of the board, e.g. a 2x3 board:
     * <br><pre>
     *   0 1 2 3
     * 0 .-. . .
     *   |R|   |
     * 1 .-.-.-.
     *   |B|
     * 2 ._._. .
     * 
     * Turn: {PLAYER}, Red: #, Blue: # , Moves: #
     * </pre>
     *
     * @return the string
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(" ");

        // first display the game board

        // top row of indices
        for (int col=0; col<=this.columns; ++col) {
            result.append(" ");
            result.append(col);
        }
        result.append("\n");

        // rows of indices and then values
        for (int row=0; row<=this.rows; ++row) {
            result.append(row);
            result.append(" ");

            // horizontal lines
            for (int column=0; column<=this.columns; ++column) {
                result.append(this.dots[row][column]);
                if (column < this.columns) {
                    result.append(this.lines.getLine(row, column, row, column + 1));
                }
            }
            result.append("\n ");

            if (row<this.rows) {
                result.append(" ");

                // vertical lines
                for (int column=0; column<=this.columns; ++column) {
                    result.append(this.lines.getLine(row, column, row+1, column));
                    if (column < this.columns) {
                        result.append(this.boxes[row][column]);
                    }
                }
                result.append("\n");
            }
        }

        // next display the game status

        if (!gameOver()) {
            result.append("\nTurn: ");
            result.append(this.player);
            result.append(", Red: ");
            result.append(this.redScore);
            result.append(", Blue: ");
            result.append(this.blueScore);

            result.append(" , Moves: ");
            result.append(this.moves);
            result.append("\n");
        } else {
            if (this.redScore > this.blueScore) {
                result.append("\nRED wins ");
                result.append(this.redScore);
                result.append(" to ");
                result.append(this.blueScore);
                result.append("!");
            } else if (this.blueScore > this.redScore) {
                result.append("\nBLUE wins ");
                result.append(this.blueScore);
                result.append(" to ");
                result.append(this.redScore);
                result.append("!");
            } else {
                result.append("\nTIE game ");
                result.append(this.redScore);
                result.append(" to ");
                result.append(this.blueScore);
                result.append("!");
            }
        }

        return result.toString();
    }
}
