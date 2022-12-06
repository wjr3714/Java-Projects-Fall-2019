
package game;

/**
 * The class Box Represents one "box" in the game of Dots & Boxes. A box is constrained by four lines: left, right,
 * top & bottom. Each box is defined by its upper left coordinate, making each box "unique" in the sense of its
 * location on the game board.
 *
 * It is used by the following classes:
 *
 * @see GameBoard
 * @see Line
 * @see Player
 *
 * @author Willliam J. Reid (wjr3714@rit.edu)
 */
public class Box {

    /**  The row that defines the upper left corner of the box. */
    private int row;
    /** The column that defines the upper left corner of the box. */
    private int column;
    /** The top line that constraints the box.*/
    private Line top; //
    /** The right line that constraints the box. */
    private Line right;
    /** The bottom line that constraints the box.*/
    private Line bottom;
    /** The left line that constraints the box.*/
    private Line left; //
    /** The collection of all four lines for a particular box. */
    private final Lines lines;
    /** The owner of the box. If the box does not have an owner, then game.Player.NONE .*/
    private Player owner = Player.NONE; // the owner of the box.

    /**
     * Creates a box and associates the correct lines with the box made.
     * @param row The upper left coordinate of the box's row.
     * @param column The upper left coordinate of the box's column.
     * @param lines The collection of all four lines for a particular box.
     */
    public Box(int row, int column, Lines lines) {
        this.row = row;
        this.column = column;
        this.lines = lines;

        top = lines.getLine(row, column, row, column + 1);
        bottom = lines.getLine(row + 1, column, row + 1, column + 1);
        left = lines.getLine(row, column,row +1, column);
        right = lines.getLine(row,column+1, row + 1, column+1 );

        top.addBox(this);
        bottom.addBox(this);
        left.addBox(this);
        right.addBox(this);
    }


    /**
     * Gets the row of the box.
     * @return The box's row as an integer.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the column of the box.
     *
     * @return The box's column as an integer.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Gets the owner of the box.
     *
     * @return The owner if it has one, Player.NONE otherwise.
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Gets the top line that defines the box.
     * @return The top line.
     */
    public Line getTopLine() {
        return this.top;
    }

    /**
     * Gets the right line that defines the box.
     * @return The right line.
     */
    public Line getRightLine() {
        return this.right;
    }

    /**
     * Gets the bottom line that defines the box.
     * @return The bottom line.
     */
    public Line getBottomLine() {
        return this.bottom;
    }

    /**
     * Gets the left line that defines the box.
     * @return The left line.
     */
    public Line getLeftLine() {
        return this.left;
    }

    /**
     * Attempts to claim a box. This method is called every time a Line is claimed. The line knows which boxes (1 or 2 only) they pertain to.
     * A box is succesfully claimed by an owner when all 4 lines that form the box have been claimed. The box is claimed by
     * the player you made the last move which resulted in a complete box being formed. Per box, this method would be called
     * exactly 4 times given that a line cannot be claimed more than once.
     * @param owner The player that claimed the last line that formed the box.
     */
    public void claim(Player owner) { //setOwner to the owner who claimed the last line (the 4th & final line of the box)
        // assert this.owner == Player.NONE;
        if (this.top.hasOwner() && this.bottom.hasOwner() && this.left.hasOwner() && this.right.hasOwner()){
            this.owner = owner;
        }
    }

    /**
     * Returns the label of the player who own's the box. Refer to: Player enum.
     * @return The label of the player who own's the box
     */
    @Override
    public String toString() {
        return owner.getLabel();
    }

    /**
     * Two boxes are equal if and only if the boxes have the same coordinates (row & column), owner and 4 lines.
     * @param other The box tho compare with.
     *
     * @return Whether or not the boxes are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Box){
            Box aBox = (Box) other;
            if (aBox.row == this.row && aBox.column == this.column){
                return true;
            }
        }
        return false;
    }

    /**
     * Computes the hash code value for equality comparisons.
     *
     * @return The hash code value of the immutable state of the instance.
     */
    @Override
    public int hashCode() {
        int exponent1 = (int) Math.pow(31,2-1);
        int exponent2 = (int) Math.pow(31, 0);
        return ((this.getRow())*exponent1 + this.getColumn()*exponent2);
    }

}
