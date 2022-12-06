package lab01.student;

import java.util.Scanner;
import static turtle.Turtle.*;

/**
 * The HTree class uses recursion and Turtle graphics to create an "h-tree" .
 * The program prompts the user to enter the depth of the recursion as a command line argument.
 * The depth of the recursion must be greater than or equal to 0.
 *
 *   *
 *  $ java HTree
 *  Usage: java HTree #
 *
 *  $ java HTree -3
 *  The depth must be greater than or equal to 0.
 *
 *  $ java HTree 0    <--- Draws nothing
 *
 *  $ java HTree 1    <--- Draws a h-tree of depth 1 (a single H)
 *
 *  $ java HTree 2    <--- Draws a h-tree of depth 2
 *
 * @author Willliam J. Reid (wjr3714)
 */
public class HTree {

    /**
     * The length of the largest segment of the H-Tree to be drawn (a constant). Per lab instructions, set to 200.
     */
    private static final int MAX_SEGMENT_LENGTH = 200;


    /**
     * The main method prompts the user for an integer to be used as the depth of the recursion and then draws the H-Tree.
     *
     * @param args Command line arguments (integer entered by user).
     */
    public  static void main(String[] args){

        // Prompt user for input.
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Enter a depth: ");
        //int depth_input = sc.nextInt();

         int depth_input = Integer.parseInt(args[0]); // Converts the depth passed by the user into an integer after
        // configuring program parameter in IntelliJ. The above statement "int depth_input = Integer.parseInt(args[0]);"
        // would be used using Intellij's run configuration supplying a "Program argument" (e.g., 3) instead of using
        // the Scanner object. Have to use one or the other, but not both.

        // qsc.close(); // Close scanner.

        if (depth_input < 0){
            System.out.println("The depth must be greater than or equal to 0.");
        }
        else{
            // Initialize Turtle.
            init​(MAX_SEGMENT_LENGTH, depth_input);

            // Draw the H-Tree.
            drawHTree​(MAX_SEGMENT_LENGTH, depth_input);

            System.out.println("Close the window to quit program.");
        }

    }

    /**
     * The method init() initializes the turtle at the center of the canvas, with the turtle facing east.
     *
     * @param length The length of one segment of the HTree.
     * @param depth The depth of the recursion.
     */
    public static void init​(int length, int depth){
        Turtle.setWorldCoordinates(-length*2, -length*2,length*2,length*2);
        Turtle.title("H-Tree, depth: " + depth);
    }

    /**
     * The method drawHTree() recursively draws the H-Tree.
     *
     * @param length The length of the H-Tree segment currently being drawn.
     * @param depth The current depth of the recusion.
     */
    public static void drawHTree​(double length, int depth){

        if (depth > 0) { // This condition prevents the recursive function from running indefinitely.
            // Start at the center of H, and move to the upper right of the H
            Turtle.speed(0); // Fastest speed of turtle.
            Turtle.forward(length / 2);
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.right(90);

            // Recurse
            HTree.drawHTree​(length / 2, depth - 1);

            // Move to lower right of the H
            Turtle.right(90);
            Turtle.forward(length);
            Turtle.left(90);

            //  Recurse
            HTree.drawHTree​(length / 2, depth - 1);

            // Move to upper left of the H
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.left(90);
            Turtle.forward(length);
            Turtle.right(90);
            Turtle.forward(length / 2);
            Turtle.right(90);

            // Recurse
            HTree.drawHTree​(length / 2, depth - 1);

            // Move to lower left of the H
            Turtle.right(90);
            Turtle.forward(length);
            Turtle.left(90);

            // Recurse
            HTree.drawHTree​(length / 2, depth - 1);

            // Return to center of the H
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.right(90);
            Turtle.forward(length / 2);
        }
    }
}
