/**
 * The Woolie simulates a Woolie crossing a TrollsBridge.
 * Each woolie has a name, a duration of time it takes the woolie to cross a bridge, a destination city (Sicstine or
 * Merctran), and a reference to troll who coordinates how woolies traverse the bridge.
 * Before crossing, a woolie has to ask the troll guarding the bridge to grant him/her access. Only after the troll grants
 * permission can the woolie begin crossing the bridge. After comopletely traversing the bridge, the woolie must notify
 * the troll and every other woolie that it is no longer on the bridge.
 *
 * @author William J. Reid (wjr3714)
 */
public class Woolie extends Thread implements Runnable {

    /** The Woolie's name. */
    private String name;

    /** The time it takes for this Woolie to cross the bridge. */
    private int crossTime;

    /** Sicstine or Merctran, the side of the river the Woolie is trying to reach. */
    private String destination;

    /** The troll guarding the bridge. */
    private TrollsBridge bridgeGuard;

    /**
     * Constructor: A new Woolie object that can run as a thread.
     *
     * @param name The Woolie's name.
     * @param crossTime The time it takes for this Woolie to cross the bridge.
     * @param destination The side of the river the Woolie is trying to reach.
     * @param bridgeGuard The troll guarding the bridge.
     */
    public Woolie(String name, int crossTime, String destination, TrollsBridge bridgeGuard){
        this.name = name;
        this.crossTime = crossTime;
        this.destination = destination;
        this.bridgeGuard = bridgeGuard;
    }

    /**
     * Takes care of the Woolie's behavior as it crosses the bridge.
     */
    public void	run(){

        boolean flag = true;
        try {
            bridgeGuard.enterBridgePlease​(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (flag) {
            for (int i = 0; i < this.crossTime; i++){
                try {
                    Thread.sleep(1000);
                    System.out.println("              " + this.name + " " + this.crossTime + " seconds.");

                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
            System.out.println(this.name + " leaves at " + this.destination + "." );
            bridgeGuard.leave();
            flag = false;
        }
    }

    /**
     * Get the Woolie's name.
     * @return The Woolie's name.
     */
    public String getWoolieName(){
        return this.name;
    }
}
