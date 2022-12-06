
package game;

/**
 * Represents a single dot in the game of Dots and Boxes.  A dot is uniquely
 * defined by a row and column coordinate.
 *
 * It is used by the following classes:
 *
 *  @see GameBoard
 *  @see Box
 *  @see Line
 *
 * @author William J. Reid (wjr3714@rit.edu)
 */
public class Dot {

    /** The string representation of a dot, "." */
    public static final String DOT = ".";
    /** The row coordinate of the dot. */
    private int row;
    /** The column coordinate of the dot. */
    private int column;


    /**
     * Create the dot.
     *
     * @param row The row
     * @param column The column
     * @pre The row and column must be greater than or equal to 0
     */
    public Dot(int row, int column) {
        assert row >= 0 && column >=0;
        this.row = row;
        this.column = column;
    }

    /**
     * Get the row.
     *
     * @return the row
     */
    public int getRow() { return this.row; }

    /**
     * Get the column.
     *
     * @return The column.
     */
    public int getColumn() {return this.column; }

    /**
     * Return the string representation of a dot.  Don't get too excited,
     * it's just a dot. :P
     *
     * @return A dot.
     */
    @Override
    public String toString() { return DOT; }

    /**
     * Two dots are equal if they have the same row and column.
     *
     * @param other Object reference to the dot to compare.
     * @return Whether they are equal or not.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Dot){
            Dot aDot = (Dot) other;
            if (aDot.row == this.row && aDot.column == this.column){
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
        return (this.getRow()*exponent1 + this.getColumn()*exponent2);
    }

}
