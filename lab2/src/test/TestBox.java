package test;

import game.Box;
import game.Dot;
import game.Line;
import game.Lines;
import game.Player;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * A test unit for the game.Box class.
 *
 * @author Sean Strout @ RIT CS
 */
public class TestBox {
    /** 2 rows */
    private final static int ROWS = 2;
    /** 3 columns */
    private final static int COLUMNS = 3;
    /** the collection of lines */
    private Lines lines;
    /** the collection of dots */
    private Dot[][] dots;

    @Before
    public void init() {
        // create the dots
        this.dots = new Dot[ROWS+1][COLUMNS+1];
        for (int row=0; row<=ROWS; ++row) {
            for (int column=0; column<=COLUMNS; ++column) {
                this.dots[row][column] = new Dot(row, column);
            }
        }

        // create the lines
        this.lines = new Lines(ROWS, COLUMNS, this.dots);
    }

    @Test
    public void testAccessors() {
        Box box1 = new Box(0, 1, this.lines);
        assertEquals(0, box1.getRow());
        assertEquals(1, box1.getColumn());
        assertEquals(Player.NONE, box1.getOwner());
        assertEquals(new Line(new Dot(0, 1), new Dot(0, 2)), box1.getTopLine());
        assertEquals(new Line(new Dot(0, 2), new Dot(1, 2)), box1.getRightLine());
        assertEquals(new Line(new Dot(1, 1), new Dot(1, 2)), box1.getBottomLine());
        assertEquals(new Line(new Dot(0, 1), new Dot(1, 1)), box1.getLeftLine());
    }

    @Test
    public void testClaim() {
        Box box1 = new Box(0, 1, this.lines);
        box1.getTopLine().claim(Player.RED);
        box1.claim(Player.RED);
        assertEquals(Player.NONE, box1.getOwner());

        box1.getRightLine().claim(Player.BLUE);
        box1.claim(Player.BLUE);
        assertEquals(Player.NONE, box1.getOwner());

        box1.getBottomLine().claim(Player.BLUE);
        box1.claim(Player.BLUE);
        assertEquals(Player.NONE, box1.getOwner());

        // the last claim should set the box owner to the red player
        box1.getLeftLine().claim(Player.RED);
        box1.claim(Player.RED);
        assertEquals(Player.RED, box1.getOwner());
    }

    @Test
    public void testStringB() {
        Box box1 = new Box(0, 1, this.lines);
        assertEquals(" ", box1.toString());
        box1.getTopLine().claim(Player.BLUE);
        box1.getRightLine().claim(Player.RED);
        box1.getBottomLine().claim(Player.RED);
        box1.getLeftLine().claim(Player.BLUE);
        box1.claim(Player.BLUE);
        assertEquals("B", box1.toString());
    }

    @Test
    public void testStringR() {
        Box box2 = new Box(1, 0, this.lines);
        assertEquals(" ", box2.toString());
        box2.getTopLine().claim(Player.RED);
        box2.getRightLine().claim(Player.BLUE);
        box2.getBottomLine().claim(Player.BLUE);
        box2.getLeftLine().claim(Player.RED);
        box2.claim(Player.RED);
        assertEquals("R", box2.toString());
    }

    @Test
    public void testEquals() {
        Box box1 = new Box(1, 0, this.lines);
        Box box2 = new Box(1, 0, this.lines);
        assertEquals(box1, box2);
    }

    @Test
    public void testNotEquals() {
        Box box1 = new Box(1, 0, this.lines);
        Box box2 = new Box(0, 1, this.lines);
        assertNotEquals(box1, box2);
    }

    @AfterClass
    public static void allDone() {
        System.out.println( TestBox.class.getSimpleName() + " All done.");
    }

}
