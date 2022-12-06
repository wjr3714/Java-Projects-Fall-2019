/*
 * RunWoolies.java
 * Provided source file as a starter for a test suite.
 * See the todo text for what to complete.
 */

/**
 * Test the TrollsBridge and Woolies simulation.
 * Test by creating a bunch of Woolies and let them cross the TrollsBridge.
 * <p>
 * Note: java -enableassertions should cause Woolies to validate their side.
 * </p>
 *
 * @author CS @ RIT.EDU
 * @author William J. Reid (wjr3714)
 */
public class RunWoolies {

    /**
     * SIDE_ONE is Merctran.
     */
    public final static String SIDE_ONE = "Merctran";

    /**
     * SIDE_TWO is Sicstine.
     */
    public final static String SIDE_TWO = "Sicstine";

    /**
     * Command interface for collecting all the functions in this test suite.
     * Single method is Command.execute().
     */
    private interface Command {
        public void execute();
    }

    /**
     * testSuite is the list of test cases.
     */
    private static Command[] testSuite = {
            new Command() {
                public void execute() {
                    RunWoolies.test0();
                }
            },
            new Command() {
                public void execute() {
                    RunWoolies.test1();
                }
            },
            new Command() {
                public void execute() {
                    RunWoolies.test2();
                }
            },
            new Command() {
                public void execute() {
                    RunWoolies.test3();
                }
            },
    };

    /**
     * TEST_COUNT is number of test cases.
     */
    public final static int TEST_COUNT = testSuite.length;

    /**
     * test0 is Test Scenario 0, an extremely simple, non-waiting test.
     * test0 provides an example template/pattern for writing a test case.
     */
    static void test0() {

        System.out.println("Begin test0. ===============================\n");

        Thread init = Thread.currentThread();      // init spawns the Woolies

        // Create a TrollsBridge of capacity 3.
        TrollsBridge trollBridge = new TrollsBridge(3);

        // Set an optional, test delay to stagger the start of each woolie.
        int delay = 4000;

        // Create the Woolies and store them in an array.
        Thread peds[] = {
                new Woolie("Al", 3, SIDE_ONE, trollBridge),
                new Woolie("Bob", 4, SIDE_TWO, trollBridge),
        };

        for (int j = 0; j < peds.length; ++j) {
            // Run them by calling their start() method.
            try {
                peds[j].start();
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.err.println("Abort. Unexpected thread interruption.");
                break;
            }
        }
        // Now, the test must give the woolies time to finish their crossings.
        for (int j = 0; j < peds.length; ++j) {
            try {
                peds[j].join();
            } catch (InterruptedException e) {
                System.err.println("Abort. Unexpected thread interruption.");
                break;
            }
        }
        System.out.println("\n=============================== End test0.");
        return;
    }

    /**
     * test1 is Test Scenario 1, another fairly simple simulation run.
     * test1 provides another example for writing a test case.
     */
    static void test1() {

        System.out.println("Begin test1. ===============================\n");

        Thread init = Thread.currentThread();      // init spawns the Woolies

        // Create a TrollsBridge of capacity 3.
        TrollsBridge trollBridge = new TrollsBridge(3);

        int delay = 1000;

        // Create the Woolies and store them in an array.
        Thread[] peds = {
                new Woolie("Al", 3, SIDE_ONE, trollBridge),
                new Woolie("Bob", 2, SIDE_ONE, trollBridge),
                new Woolie("Cathy", 2, SIDE_TWO, trollBridge),
                new Woolie("Doris", 3, SIDE_TWO, trollBridge),
                new Woolie("Edith", 3, SIDE_ONE, trollBridge),
                new Woolie("Fred", 2, SIDE_TWO, trollBridge),
        };

        for (int j = 0; j < peds.length; ++j) {
            // Run them by calling their start() method.
            try {
                peds[j].start();
                Thread.sleep(delay);         // delay start of next woolie
            } catch (InterruptedException e) {
                System.err.println("Abort. Unexpected thread interruption.");
            }
        }
        // Now, the test must give the woolies time to finish their crossings.
        for (int j = 0; j < peds.length; ++j) {
            try {
                peds[j].join();              // wait for next woolie to finish
            } catch (InterruptedException e) {
                System.err.println("Abort. Unexpected thread interruption.");
            }
        }

        System.out.println("\n=============================== End test1.");
    }

    /**
     * Test Case #2: Added additional Woolies to back up the queue a bit more.
     *
     * @author William J. Reid (wjr3714)
     */
    static void test2() {

        System.out.println("Begin test2. ===============================\n");

        Thread init = Thread.currentThread();      // init spawns the Woolies

        //
        // Create a TrollsBridge of capacity 3.
        TrollsBridge trollBridge = new TrollsBridge(3);

        // Set an OPTIONAL, test delay to stagger the start of each woolie.
        int delay = 1234;

        // Create the Woolies and store them in an array.
        Thread[] woolieArray = {
                new Woolie("Alicia", 3, SIDE_ONE, trollBridge),
                new Woolie("Brian", 5, SIDE_ONE, trollBridge),
                new Woolie("Carlos", 6, SIDE_TWO, trollBridge),
                new Woolie("Dorothy", 3, SIDE_TWO, trollBridge),
                new Woolie("Dylan", 5, SIDE_ONE, trollBridge),
                new Woolie("Edith", 4, SIDE_ONE, trollBridge),
                new Woolie("Jake", 2, SIDE_TWO, trollBridge),
                new Woolie("Karla", 5, SIDE_TWO, trollBridge),
                new Woolie("Meghan", 4, SIDE_TWO, trollBridge),
                new Woolie("Piaf", 5, SIDE_ONE, trollBridge),
                new Woolie("Roberto", 2, SIDE_TWO, trollBridge),
        };

        // Run them by calling their start() method.
        for (Thread value : woolieArray) {
            try {
                value.start();
                Thread.sleep(delay);         // delay start of next woolie
            } catch (InterruptedException e) {
                System.err.println("Abort. Unexpected thread interruption.");
            }
        }

        // Now, the test must give the woolies time to finish their crossings.
        for (Thread thread : woolieArray) {
            try {
                thread.join();              // wait for next woolie to finish
            } catch (InterruptedException e) {
                System.err.println("Abort. Unexpected thread interruption.");
            }
        }
        System.out.println("TODO: follow the pattern of the example tests.");
        System.out.println("\n=============================== End test2.");
    }

    /**
     * Test Case #3: Added even more additional Woolies to back up the queue more.
     *
     * @author William J. Reid (wjr3714)
     */
    static void test3() {

        System.out.println("Begin test3. ===============================\n");

        Thread init = Thread.currentThread();      // init spawns the Woolies


        // Create a TrollsBridge of capacity 3.
        TrollsBridge trollBridge = new TrollsBridge(3);

        // Set an OPTIONAL, test delay to stagger the start of each woolie.
        int delay = 347;

        // Create the Woolies and store them in an array.
        Thread[] woolieArray = {
                new Woolie("Agustina", 7, SIDE_ONE, trollBridge),
                new Woolie("Beatrice", 5, SIDE_ONE, trollBridge),
                new Woolie("Becca", 5, SIDE_ONE, trollBridge),
                new Woolie("Charlie", 6, SIDE_TWO, trollBridge),
                new Woolie("Chip", 6, SIDE_TWO, trollBridge),
                new Woolie("Daniel", 3, SIDE_TWO, trollBridge),
                new Woolie("Danielle", 5, SIDE_ONE, trollBridge),
                new Woolie("Edward", 4, SIDE_ONE, trollBridge),
                new Woolie("Erik", 4, SIDE_ONE, trollBridge),
                new Woolie("Jacob", 7, SIDE_TWO, trollBridge),
                new Woolie("José", 3, SIDE_TWO, trollBridge),
                new Woolie("Kimberly", 5, SIDE_TWO, trollBridge),
                new Woolie("Leonardo", 6, SIDE_TWO, trollBridge),
                new Woolie("Luis", 7, SIDE_TWO, trollBridge),
                new Woolie("Laura", 4, SIDE_TWO, trollBridge),
                new Woolie("Maria", 5, SIDE_TWO, trollBridge),
                new Woolie("Meghan", 3, SIDE_TWO, trollBridge),
                new Woolie("Pablo", 5, SIDE_ONE, trollBridge),
                new Woolie("Pedro", 4, SIDE_ONE, trollBridge),
                new Woolie("Piaf", 5, SIDE_ONE, trollBridge),
                new Woolie("Roberto", 2, SIDE_TWO, trollBridge),
                new Woolie("Sara", 3, SIDE_TWO, trollBridge),
                new Woolie("Soriana", 7, SIDE_TWO, trollBridge),
        };

        // Run them by calling their start() method.
        for (Thread value : woolieArray) {
            try {
                value.start();
                Thread.sleep(delay);         // delay start of next woolie
            } catch (InterruptedException e) {
                System.err.println("Abort. Unexpected thread interruption.");
            }
        }

        // Now, the test must give the woolies time to finish their crossings.
        for (Thread thread : woolieArray) {
            try {
                thread.join();              // wait for next woolie to finish
            } catch (InterruptedException e) {
                System.err.println("Abort. Unexpected thread interruption.");
            }
        }


        System.out.println("\n=============================== End test3.");
    }

    /**
     * Run all the tests in this test suite.
     *
     * @param args not used
     */
    public static void main(String args[]) {

        for (int j = 0; j < TEST_COUNT; ++j) {
            testSuite[j].execute();
        }
    }

}

