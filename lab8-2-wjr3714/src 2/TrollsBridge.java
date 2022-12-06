
/**
 * The TrollsBridge is a bridge guarded by a troll.
 *
 * @author William J. Reid (wjr3714)
 */
public class TrollsBridge {

    /** The maximum number of Woolies allowed on the bridge at any given time (inclusive). */
    private int maxOnBridge;

    /** The current number of Woolies on the bridge. */
    private int numOnBridge;

    /**
     * Create a TrollsBridge with a given capacity.
     * @param max The maximum capacity of the TrollsBridge.
     */
    public TrollsBridge(int max){
        this.maxOnBridge = max;
        this.numOnBridge = 0;
    }

    /**
     * Request permission to go onto the troll's bridge.
     */
    public synchronized void enterBridgePlease​(Woolie thisWoolie) throws InterruptedException {

        while (maxOnBridge == numOnBridge) {
            try {
                System.out.println("The troll scowls \"Get in line!\" when  " + thisWoolie.getWoolieName() + " shows up at the bridge.");
                wait();
            }
            catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        numOnBridge = numOnBridge + 1;
        System.out.println("The troll scowls \"Get in line!\" when  " + thisWoolie.getWoolieName() + " shows up at the bridge.");
        System.out.println(thisWoolie.getWoolieName() + " is starting to cross.");

    }

    /**
     * Tell the troll of a TrollsBridge that a Woolie has left the bridge so that the troll can let other woolies get
     * on if there is room.
     */
    public synchronized void leave(){
        numOnBridge--;
        notifyAll();
    }
}
