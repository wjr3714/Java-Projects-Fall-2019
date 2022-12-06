
package game;

import org.junit.Test;

import java.util.ArrayList;

/**
 * This class represents a single line in the Dots & Boxes game. A line is defined by two coordinates that are one unit apart,
 * either horizontally or vertically. The line must be defined left to right or top to bottom.
 *
 * It is used by the following classes:
 *
 *  @see GameBoard
 *  @see Box
 *  @see Lines
 *  @see Player
 *
 * @author William J. Reid (wjr3714@rit.edu)
 */
public class Line {

    /** The character that will be drawn on the board if no line has been claimed. */
    public static final String EMPTY = " ";
    /** The horizontal line that will be drawn on the game board. */
    public static final String HORI_LINE = "-";
    /** The vertical line that will be drawn on the game board. */
    public static final String VERT_LINE = "|";
    /** The first coordinate of the line*/
    private  Dot first;
    /** The second coordinate of the line */
    private  Dot second;
    /** The player who claimed (drew) the line. */
    private Player owner; //owner of the line
    /** All the boxes the line belongs to. The line will either belong to 1 or 2 boxes. */
    private ArrayList<Box> boxes;

    /**
     * Create the line.
     *
     * @param first The first coordinate of the line.
     * @param second The second coordinate of the line.
     * @pre The horizontal line must be specified left to right, the vertical line must be specified top to bottom, and the line must be 1 unit in length.
     */
    public Line(Dot first, Dot second) {
        assert first.getRow() <= second.getRow();
        assert first.getColumn() <=  second.getColumn();
        assert  (second.getColumn() - first.getColumn()) < 2;
        assert  (second.getRow() - first.getRow()) < 2;
        this.first = first;
        this.second = second;
        this.owner = Player.NONE;
        boxes = new ArrayList<>();
    }

    /**
     * Get the first coordinate of the line.
     *
     * @return The first dot.
     */
    public Dot getFirst() { return this.first; } //get the first coordinate (start of line)

    /**
     * Get the second coordinate of the line.
     *
     * @return The second dot.
     */
    public Dot getSecond() { return this.second; } //get the second coordinate (end of line)

    /**
     * Get the player who claimed (drew) the line.
     *
     * @return The player who owns the line.
     */
    public Player getOwner() { return this.owner; }

    /**
     * Check if the line has been claimed by a player.
     *
     * @return Returns false if line has not been claimed, true otherwise.
     */
    public boolean hasOwner() { return this.owner != Player.NONE; } // Check if the line has been claimed, returns false if line has not been claimed.

    /**
     * Claim a line, and possibly the line's associated boxes if the last line required to complete the box(es) was claimed in this player's move.
     *
     * @param owner The player who claimed the line.
     */
    public void claim(Player owner) {
        this.owner = owner;

        for (Box box : boxes){
            box.claim(owner);
        }

    }

    /**
     * Gets the boxes associated with this line.
     *
     * @return The boxes associated with this line.
     */
    public ArrayList<Box> getBoxes() { return this.boxes; }

    /**
     * Add a box to the set associated with this line.
     *
     * @param box The box to add to the set associated with this line.
     */
    public void addBox(Box box){
        boxes.add(box);
    }

    /**
     * Returns a string representation of the line, " " for empty, "-" for a horizontal line, and "|" for a vertical line.
     *
     * @return The string representation of the line drawn.
     */
    public String toString() {

        if (!hasOwner()){
            return EMPTY;
        }
        else if (first.getRow() == second.getRow()){ // If the rows of the two dots are are the same, then draw a horizontal line.
            return HORI_LINE;
        }
        else if (first.getColumn() == second.getColumn()){ //If the columns of the two dots are are the same, then draw a vertical line.
            return VERT_LINE;
        }
        else{
            return EMPTY;
        }

    }

    /**
     * Checks if two lines are equal. Two lines are equal if their first and second dots are equal.
     *
     * @param other The line to compare with.
     * @return True if the lines are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Line){
            Line aLine = (Line) other;
            if (aLine.first.equals(this.first) && aLine.second.equals(this.second)){
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
        int exponent1 = (int) Math.pow(31,4-1);
        int exponent2 = (int) Math.pow(31,3-1);
        int exponent3 = (int) Math.pow(31,2-1);
        int exponent4 = (int) Math.pow(31, 0);
        return (first.getRow()*exponent1 + second.getRow()*exponent2 + first.getColumn()*exponent3 + second.getColumn()*exponent4);
    }

}
