/*
 * file: NurikabeConfig.java
 * Configuration and functions for backtracking solution to Nurikabe puzzle.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * A class to represent a single configuration in the Nurikabe puzzle.
 *
 * @author Sean Strout @ RITCS
 * @author William J. Reid (wjr3714)
 */
public class NurikabeConfig implements Configuration {

    /** The number of rows in the Nurikabe gameboard. */
    private static int r;

    /** The number of columns in the Nurikabe gameboard. */
    private static int c;

    /** The data structure containing the representation of the Nurikabe board. */
    private String[][] board;

    /** Keeps track of the last move's row index. */
    private static int nextMoveRow = 0;

    /** Keeps track of the last move's column index. */
    private static int nextMoveCol = 0;

    /** Total number of islands on the board. */
    private int totalIslands;

    /**
     * Construct the initial configuration from an input file whose contents
     * are, for example:<br>
     * <tt><br>
     * 3 3          # rows columns<br>
     * 1 . #        # row 1, .=empty, 1-9=numbered island, #=island, &#64;=sea<br>
     * &#64; . 3    # row 2<br>
     * 1 . .        # row 3<br>
     * </tt><br>
     * @param filename the name of the file to read from
     * @throws FileNotFoundException if the file is not found
     */
    public NurikabeConfig(String filename) throws FileNotFoundException {

        int count = 0;
        boolean flag = true;
        try (Scanner in = new Scanner(new File(filename))) {

            while (in.hasNextLine() && count <= r && flag){

                // Create board
                if (count == 0) {
                    r = in.nextInt();
                    c = in.nextInt();
                    board  = new String[r][c];
                    count++;
                }
                else {
                    count++;
                    if (in.nextLine().equals("")){
                        flag = false;
                    }
                    // Populate initial board
                    for (int i = 0; i < r; i++){
                        for (int j = 0; j < c; j++){
                            String character = in.next();
                            if (character.equals(".")){
                                board[i][j] = ".";
                            }
                            else{
                                board[i][j] = character;
                                totalIslands += Integer.parseInt(character);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Copy constructor takes a configuration, other, and makes a full "deep" copy
     * of its instance data.
     *
     * @param other The configuration to copy.
     */
    protected NurikabeConfig(NurikabeConfig other) {
        this.board = new String[r][c];
        for (int i = 0; i < r; i++) {
            if (c >= 0) System.arraycopy(other.board[i], 0, this.board[i], 0, c);
        }
    }

    /**
     * Gets the successors of the current configuration.
     * @return The two possible successors of the current configuration.
     */
    @Override
    public Collection<Configuration> getSuccessors() {
         Collection<Configuration> successors = new ArrayList<Configuration>();
         for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                nextMoveRow = i;
                nextMoveCol = j;
                if (board[i][j].equals(".")){
                    NurikabeConfig child1 = new NurikabeConfig(this);
                    child1.board[i][j] = "@";
                    successors.add(child1);
                    NurikabeConfig child2 = new NurikabeConfig(this);
                    child2.board[i][j] = "#";
                    successors.add(child2);
                    return successors;
                }
            }
        }
        return successors;
    }

    /**
     * Checks whether the last move of the game was valid.
     *
     * Rules:
     * The non-numbered cells must be colored as islands or sea, subject to the following rules:
     * Each numbered cell is an island cell and the number in it is the number of cells in that island.
     * Each island must contain exactly one numbered cell.
     * There must be only one connected sea. The sea cells must be connected in one of the four primary directions, not diagonally.
     * The sea must not contain "pools", i.e. 2x2 areas of blue cells.
     *
     * @return True if the last move did NOT break any rules of the game, false otherwise.
     */
    @Override
    public boolean isValid() {

        int islandCounter = 0;
        boolean flag = true;
        int checkTotalIslands = 0;
        int[] deltaROW = {1, 0, 0, -1};
        int[] deltaCOLUMN = {0, 1, -1, 0};
        for (int i = 0; i < 4; i ++) {
            int nextROW = nextMoveRow + deltaROW[i];
            int nextCOLUMN = nextMoveCol + deltaCOLUMN[i];


            // There can be no islands "#" adjacent to a "1" in the board.
            if (board[nextMoveRow][nextMoveCol].equals("#") && 0 <= nextROW && nextROW < r && 0 <= nextCOLUMN && nextCOLUMN < c){
                if((board[nextROW][nextCOLUMN].equals("1"))){
                    return false;
                }
            }
            // Any numbered cell containing a value greater than 1 must be connected to a island or to an empty cell (if not populated yet)
            if ((!board[nextMoveRow][nextMoveCol].equals(".")) && (!board[nextMoveRow][nextMoveCol].equals("@")) &&
                    (!board[nextMoveRow][nextMoveCol].equals("#")) && (!board[nextMoveRow][nextMoveCol].equals("1"))
                    && 0 <= nextROW && nextROW < r && 0 <= nextCOLUMN && nextCOLUMN < c ){
                if (board[nextROW][nextCOLUMN].equals("#")){
                    islandCounter = -4;
                    continue;
                }
            }
            if (0 <= nextROW && nextROW < r && 0 <= nextCOLUMN && nextCOLUMN < c) {
                if (!this.board[nextROW][nextCOLUMN].equals(".") && !this.board[nextROW][nextCOLUMN].equals("@")){
                    checkTotalIslands = checkTotalIslands + 1;
                }
            }
            islandCounter = islandCounter + 1;
        }
        if (islandCounter > 0){
            flag = false;
        }

        if (checkTotalIslands > totalIslands){
            return false;
        }

        return flag && noPools(); // Stop populating branch early if pool is found (pruning).
    }

    /**
     * Checks whether the end goal has been reached (a valid solution).
     * @return True if the configuration is the soluton, false otherwise.
     */
    @Override
    public boolean isGoal() {
        // Check entries in reverse order to optimize efficiency (from bottommost right to topmost left).
        for (int i = r - 1; i == 0; i--){
            for (int j = c - 1; j == 0; j--) {
                if (board[i][j].equals(".")){
                    return false;
                }
            }
        }

        return noPools() && searchBFS();
    }

    /**
     * Returns a string representation of the Nurikabe board, e.g.: <br>
     * <tt><br>
     * 1 . #<br>
     * &#64; . 3<br>
     * 1 . .<br>
     * </tt><br>
     */
    @Override
    public String toString() {
        // TODO
        StringBuilder currentBoard = new StringBuilder();
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                if (j < c - 1) {
                    currentBoard.append(board[i][j]).append(" ");
                }
                else if (j == c - 1){ // Don't append an extra space character at the end of each line.
                    currentBoard.append(board[i][j]).append("\n");
                }
            }
        }
        return currentBoard.toString();
    }


    /**
     * Check if any pools exist in the board. Used as one of the pruning techniques.
     * @return True if no pools exists, false otherwise.
     */
    private boolean noPools() {
        for (int i=0; i<r-1; i++) {
            for (int j = 0; j < c - 1; j++) {
                if (board[i][j].equals("@") && board[i][j + 1].equals("@") && board[i + 1][j].equals("@") && board[i + 1][j + 1].equals("@")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Perform breadth first search to ensure the sea is entirely connected and every island is complete.
     * @return True if the sea is entirely connected, false otherwise.
     */
    private boolean searchBFS(){

        //Initialize the queue for the BFS, add (i,j) to it and mark it visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        // Keep track of cells visited not to traverse them twice
        boolean[][] visited = new boolean[r][c];
        // Helper arrays for traversing the board.
        int[] dx = {-1,0,1,0};
        int[] dy = {0,1,0,-1};

        for (int i=0; i<r-1; i++) {
            for (int j = 0; j < c - 1; j++) {
                if (board[i][j].equals("@")){
                    // Add coordinates of starting location (first sea encountered.
                    queue.add(i);
                    queue.add(j);
                    visited[i][j] = true;
                    break;
                }
            }
        }

        int cellsTouching = 0;

        //Perform the BFS
        while(!queue.isEmpty()) {
            int row = queue.remove();
            int col = queue.remove();

            cellsTouching++;

            //Iterate in all 4 directions
            for(int k = 0; k < 4; k++) {
                int x = row + dx[k];
                int y = col + dy[k];

                //Out of bounds check & previously visited check.
                if(x < 0 || visited[x][y]) {
                    continue;
                }
                else {
                    visited[x][y] = true;
                    queue.add(x);
                    queue.add(y);
                }
            }

        }
        return cellsTouching == ((r*c) - totalIslands); // The total number of sea cells touching must equal the total
                                                        // number of sea cells in the board..
    }

    /**
     * Check whether two different islands numbers are connected.
     * @return True if two different islands numbers are connected, false otherwise.
     */
    private boolean searchDFS(){
        return false;
    }
}





