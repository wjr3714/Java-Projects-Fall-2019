package rit.stu.act2;

// Use the QueueNode implemented in Activity #1
import rit.stu.act1.QueueNode;

/**
 * A class that represents the bunker of soldiers, which is where the soldiers are stationed. The bunker is a QueueNode.
 * The first soldier in, is the first soldier out.
 * @see QueueNode
 * @see Soldier
 * @author William J. Reid (wjr3714)
 */
public class Bunker{

    // Use the methods implemented in the QueueNode class to implement the methods below.

    /** The queue of soldiers currently in the bunker. */
    private QueueNode<Soldier> bunker;

    /** The number of soldiers currently in the bunker. */
    private int numSoldiers;

    /** Create the bunker. */
    public Bunker(int numSoldiers){
        this.numSoldiers = numSoldiers;
        this.bunker = new QueueNode<>();
        for (int id = 1; id <= numSoldiers; id++){
            Soldier soldier = new Soldier(id);
            bunker.enqueue(soldier);
        }
    }

    /**
     * Remove the next soldier from the front of the bunker to be deployed on a rescue attempt.
     * @pre Assert the bunker is not empty.
     * @return The soldier at the front of the bunker (QueueNode).
     */
    public Soldier deployNextSoldier(){
        assert !bunker.empty();
        this.numSoldiers--;
        return bunker.dequeue();
    }

    /**
     * Adds a new soldier to the end of the bunker (QueueNode).
     * @param soldier The new soldier to add to the end of the bunker (QueueNode).
     */
    public void fortifySoldiers(Soldier soldier){
        this.numSoldiers++;
        bunker.enqueue(soldier);
    }

    /**
     * Get how many soldiers are in the bunker.
     * @return The number of soldiers left in the bunker.
     */
    public int getNumSoldiers(){
        return this.numSoldiers;
    }

    /**
     * Checks if there are soldiers left in the bunker.
     * @return True if the number of soldiers in the bunker ≥ 0, false otherwise.
     */
    public boolean hasSoldiers(){
        return !this.bunker.empty();
    }
}
