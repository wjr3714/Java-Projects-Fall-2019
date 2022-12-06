package Players.Wjr3714;

import Interface.Coordinate;
import java.util.ArrayList;

/**
 * Graph representation of the game Pathbuilder.
 *
 * @author William J. Reid (wjr3714)
 */
public class Pathbuilder_Graph {

    /** The board of the game is represented by a nested array. */
    private int[][] graph;

    /** Number of rows on graph. */
    private int row;

    /** Number of columns on graph. */
    private int column;

    /**
     * Creates a graph representation of the Pathbuilder game.
     * @param dim Dimension of the Pathbuilder's game board.
     */
    public Pathbuilder_Graph(int dim) {
        this.row = 2 * dim + 1;
        this.column = 2 * dim + 1;

        this.graph = new int[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                this.graph[i][j] = 0;
            }
        }
    }

    /**
     * The breadth first search is used to determine if a path connects the left side of the board to the right side
     * of the board (if this is true, player 1 has won) or if path connects the top side of the board to the bottom side
     * of the board (if this is true, player 2 has won). Note: it is only possible for one of the two players to win.
     *
     * @param path The list coordinates to check for ownership and connection.
     * @param playerID The player's ID
     * @return True if a path exists from the left side of the board to the right side of the board (player 1 has won) or
     * if path connects the top side of the board to the bottom side of the board (player 2 has won), false otherwise.
     */
    public boolean searchBFS(ArrayList<Coordinate> path, int playerID){

        // Initially, no coordinates have been visited.
        boolean[][] visitedCoordinates = new boolean[row][column]; // Sets every entry in 2D array to false.

        ArrayList<Coordinate> queueCoordinates = new ArrayList<>();
        for (Coordinate coordinate : path){
            queueCoordinates.add(coordinate);
            int r = coordinate.getRow();
            int c = coordinate.getCol();
            visitedCoordinates[r][c] = true;
        }


        /*
        Used to help check neighboring coordinates of current coordinate.

        Explanation of Logic (used with for loop):

        ( deltaROW , deltaCOLUMN )

        ( 1 , 0 )  <-- Check coordinate directly below the current coordinate.
        ( 0 , 1 )  <-- Check coordinate directly to the right of current coordinate.
        ( 0 , -1 ) <-- Check coordinate directly to the left of current coordinate.
        ( -1 , 0 ) <-- Check coordinate directly above the current coordinate.
        */
        int[] deltaROW = {1, 0, 0, -1};
        int[] deltaCOLUMN = {0, 1, -1, 0};

        while (!queueCoordinates.isEmpty()){

            Coordinate rc = queueCoordinates.remove(0);
            int r = rc.getRow();
            int c = rc.getCol();

            for (int i = 0; i < 4; i ++) {
                int nextROW = rc.getRow() + deltaROW[i];
                int nextCOLUMN = rc.getCol() + deltaCOLUMN[i];

                // Check if coordinate is valid (is it within the game's dimensions?)
                // Note: row, column ≠ negative nor greater than the dimension of the board.
                if (0 <= nextROW && nextROW < row && 0 <= nextCOLUMN && nextCOLUMN < column) {

                    // For each coordinate, need to check the coordinate directly above, below, left and right of
                    // the current coordinate.
                    if (this.graph[nextROW][nextCOLUMN] == playerID) {
                        nextROW += deltaROW[i];
                        nextCOLUMN += deltaCOLUMN[i];

                        // Do not repeat work if coordinate already visited.
                        if (!visitedCoordinates[nextROW][nextCOLUMN]) {
                            queueCoordinates.add(new Coordinate(nextROW, nextCOLUMN));
                            visitedCoordinates[nextROW][nextCOLUMN] = true;
                        }
                    }
                }
            }

            // Check if path exists for player 1 & player 2
            if ( (playerID == 1 && visitedCoordinates[r][column - 1]) || (playerID == 2 && visitedCoordinates[row - 1][c]) ) {
                return true;
            }
        }
        return false; // If no path from start to finish was found, this method should reach this statement (no player has won yet).
    }

    /**
     * Gets the board of the game.
     * @return The graph of the game.
     */
    public int[][] getPathbuilderGraph(){
        return graph;
    }

    /**
     * Get the board's dimensions. Note: the board is always a square, so we only need to know the number of columns or
     * rows, but not both.
     * @return The dimension of the board.
     */
    public int getGraphDimensions(){
        return this.column;
    }
}
