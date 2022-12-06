
package game;

//import java.util.Arrays;
import java.util.Scanner;

/**
 * The main program for the game Dots & Boxes. The program is run on the command line as:
 *
 * $ java game.DotsAndBoxes rows columns
 *
 * The game has two players, player RED and player BLUE. When prompted, the RED player (which always starts the game)
 * enters two coordinates to draw a line between two dots on the board. The coordinates are entered as follows:
 * horizontal lines are entered from left to right while vertical lines are entered from top to bottom. The line coordinates
 * are four integers greater than or equal to zero and must be separated by a "space" character in the command line. The
 * character ">" in the command line indicates that the player must enter the coordinates of the line to draw. These coordinates
 * must be provided in the following order:
 *
 * > row1 column1 row2 column2
 *
 * Where:
 * row1 = Topmost row coordinate of the line.
 * column1 = Leftmost column coordinate of the line.
 * row2 = Bottom-most row coordinate of the line.
 * column2 = Rightmost column coordinate of the line.
 *
 * Note: A line is ONE unit in length.
 *
 * To prematurely terminate the game of Dots & Boxes, the user should enter 'q' on the command line during any player's turn.
 *
 * @author William J. Reid (wjr3714@rit.edu)
 */
public class DotsAndBoxes {

    /** Where user enters input. */
    public static final String PROMPT = "> ";
    /** The scanner object used to read user input throughout the game.*/
    private  Scanner input = new Scanner(System.in);
    /** The number of rows of the game Dots & Boxes */
    private final int rows;
    /** The number of columns of the game Dots & Boxes */
    private final int columns;
    /** The actual gameboard where the game Dots & Boxes will be played.*/
    private  GameBoard gameBoard;

    // Constructor
    /**
     * Create the game of Dots & Boxes.
     *
     * @param rows the row
     * @param columns the column
     */
    private DotsAndBoxes(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * The main method instantiates the game with the number of rows and columns provided by the user through the command line.
     * The main method also plays the game Dots & Boxes.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Check if the two required program arguments (#rows & #columns) were passed in as command line arguments.
        if (args.length != 2) {
            System.out.println("Usage: java game.DotsAndBoxes rows columns");
        }
        else {

            // Create the game with the given #rows & #columns passed in the command line by the user.
            DotsAndBoxes game = new DotsAndBoxes(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

            // Create the game's board with given  #rows & #columns passed in the command line by the user.
            game.gameBoard = new GameBoard(game.rows, game.columns);

            // Begin playing the game of Dots & Boxes!
            game.play();
        }
    }

    /**
     * Plays the game until user enters a "q" or the end of the the game is reached (when all lines on the game board
     * have been claimed.)
     */
    public void play(){ // Keep playing until user enters "q".

        // Keep playing until the end of the game is reached.
        while (!gameBoard.gameOver()){

            // Display empty board
            System.out.println(gameBoard.toString());

            boolean moveValid = makeMove(input);
            // Allow the current player to correct a bad input.
            while (!moveValid){
                moveValid = makeMove(input);
            }
        }
        // When the game is over, print the score to the screen.
        System.out.println(gameBoard.toString());

        input.close(); // close the scanner object at the end of the game.
    }

    /**
     * The makeMove function does a single move in the game for the current player.
     *
     * @param sc Scanner object used to read the user's input.
     * @return Whether the move was valid!
     */
    private boolean makeMove(Scanner sc){

        // Print current player's name
        System.out.println(gameBoard.whoseTurn() + "'s move...");

        // Request user input
        System.out.print(PROMPT);

        String[] line = sc.nextLine().split("\\s+"); // parses a String separated by multiple spaces. In Java, \s denotes a whitespace. The "+" sign means to split at the String at the multiple white spaces.
        // Troubleshooting --> System.out.println(Arrays.toString(line));

        // End the game if the user enters "q".
        if (line[0].equals("q")){
            input.close();
            System.exit(0); // Exit the program with no further output and without throwing any errors
            return true;
        }
        // Verify that the coordinates given are not negative. Otherwise, ask the user to re-enter the line's coordinates.
        else if ((Integer.parseInt(line[0]) < 0) || (Integer.parseInt(line[1]) < 0) || (Integer.parseInt(line[2]) < 0) || (Integer.parseInt(line[3]) < 0)){
            System.out.println("Invalid Line! Coordinates cannot be negative! \n"+ gameBoard.whoseTurn() +"'s move again. \n"+ gameBoard.whoseTurn() + ", please enter valid line coordinates. \n");
            System.out.println(gameBoard.toString());
            return false; // This will prompt the same player to correct the input.
        }
        else {
            // Store each of of the 4 coordinates in a separate int variable.
            int row1 = Integer.parseInt(line[0]);
            int column1 = Integer.parseInt(line[1]);
            int row2 = Integer.parseInt(line[2]);
            int column2 = Integer.parseInt(line[3]);

            // Check if the line is valid using the GameBoard class.
            if (!gameBoard.isLineValid(row1, column1, row2, column2)) {
                System.out.println("Invalid Line! \n"+ gameBoard.whoseTurn() +"'s move again. \n"+ gameBoard.whoseTurn() + ", please enter valid line coordinates. \n");
                System.out.println(gameBoard.toString());
                return false; // This will prompt the same player to correct the input.
            }
            else {
                // Make a successful move.
                gameBoard.makeMove(row1, column1, row2, column2);
                return true; // Unless we have reached the end of the game, we want to keep playing.
                // This will keep the play() method running, and prompt the player for the next move.
            }
        }
    }
}



