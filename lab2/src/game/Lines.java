/*
 * file: Lines.java
 * Lines is a provided part of the DotsAndBoxes program solution.
 *
 * CS.RIT.EDU
 * A student should NOT make any changes to this code.
 */

package game;

import java.util.ArrayList;

/**
 * This class holds the collection of all lines in the game of Dots and Boxes.
 * It is used by the following classes:
 *
 * @see GameBoard
 * @see Box
 *
 * @author Sean Strout @ RIT CS
 */
public class Lines {
    /** holds the collection of lines */
    private ArrayList<Line> lines;

    /**
     * Creates all the lines in the game.  We assume that all lines are
     * specified from left to right (horizontal) or top to bottom
     * (vertical) For a rectangular grid assume that m = rows+1 and n = columns+1.
     * Therefore there will be 2mn - m - n lines in the grid.  This site
     * summarizes it:<br><br>
     *
     * <a href="http://mathworld.wolfram.com/GridGraph.html">Grid Graph</a>.
     *
     * @param rows the number of rows
     * @param columns the number of columns
     * @param dots the grid of dots
     */
    public Lines(int rows, int columns, Dot[][] dots) {
        final int m = rows + 1;
        final int n = columns + 1;

        // build the lines given:
        // # of lines = 2 * m * n - m - n
        this.lines = new ArrayList<>(2*m*n - m - n);
        for (int row=0; row<m; ++row) {
            for (int column=0; column<n; ++column) {
                // horizontal
                if (column<n-1) {
                    this.lines.add(new Line(dots[row][column], dots[row][column+1]));
                }

                // vertical
                if (row<m-1) {
                    this.lines.add(new Line(dots[row][column], dots[row+1][column]));
                }
            }
        }
    }

    /**
     * Get a line by two coordinates.  Note that in order to find the line
     * it must be specified left to right (horizontal) or top to bottom
     * (vertical) or else it won't be found.
     *
     * @param row1 the first row
     * @param column1 the first column
     * @param row2 the second row
     * @param column2 the second column
     *
     * @return the line, or null if it can't be found
     */
    public Line getLine(int row1, int column1, int row2, int column2) {
        for (Line line : this.lines) {
            if (line.getFirst().getRow() == row1 &&
                    line.getFirst().getColumn() == column1 &&
                    line.getSecond().getRow() == row2 &&
                    line.getSecond().getColumn() == column2) {
                return line;
            }
        }

        // should never get here unless coordinates are invalid
        return null;
    }

    /**
     * Get the number of lines in the collection.
     *
     * @return number of lines
     */
    public int size() { return this.lines.size(); }
}
