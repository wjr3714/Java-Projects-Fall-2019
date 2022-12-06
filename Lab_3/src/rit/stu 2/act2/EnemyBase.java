package rit.stu.act2;

import java.util.Random;
import rit.stu.act1.QueueNode;
import rit.stu.act1.StackNode;

/**
 * This class represents the enemy base. It contains a guard line of guerillas (QueueNode). The guerillas are guarding all hostages
 * stored in a cave (StackNode) with a narrow entrance that prevents the hostages from reordering themselves.
 * @see QueueNode
 * @see StackNode
 * @see Player
 * @see Hostage
 * @see Guerilla
 * @author William J. Reid (wjr3714)
 */
public class EnemyBase {

    /** The guerillas are waiting in a guard line (QueueNode). */
    private QueueNode<Guerilla> guerillas;

    /** The hostages are imprisoned in a cave, which is a stack. */
    private StackNode<Hostage> hostages;

    /** The total number of guerillas in the guard line. */
    private int numGuerillas;

    /** The number of hostages currently imprisoned in the cave (stack). */
    private int numHostages;

    /**
     * Create the enemy base with a number of hostages, 1-numHostages, pushed into the cave (StackNode) in order, and a number of guerillas,
     * 1-numGuerillas, added to the guard line (QueueNode) in order.
     * @param numHostages The number of hostages in the enemy base.
     * @param numGuerillas The number of guerillas in the enemy base.
     */
    public EnemyBase(int numHostages, int numGuerillas){

        this.numHostages = numHostages;
        this.hostages = new StackNode<Hostage>();
        this.numGuerillas = numGuerillas;
        this.guerillas = new QueueNode<Guerilla>();

        // Move hostages into the cave.
        for (int id = 1; id <= numHostages; id++){
            Hostage hostage = new Hostage(id);
            hostages.push(hostage);
        }

        // Add guerillas into the guard line.
        for (int id = 1; id <= numGuerillas; id++){
            Guerilla guerilla = new Guerilla(id);
            guerillas.enqueue(guerilla);
        }
    }

    /**
     * Add a guerilla to the end of the guard line.
     * @param guerilla The guerilla to add to the guard line.
     */
    private void addGuerilla(Guerilla guerilla){
        this.guerillas.enqueue(guerilla);
        this.numGuerillas++;
    }

    /**
     * Add a hostage to the front of the cave.
     * @param hostage The hostage to add to the front (top) of the cave (StackNode).
     */
    private void addHostage(Hostage hostage){
        this.hostages.push(hostage);
        this.numHostages++;
    }

    /**
     * Remove a guerilla from the front of the guard line (QueueNode).
     * @return The guerilla removed from the front of the guard line (QueueNode).
     * @pre The guerilla line is not empty.
     */
    private Guerilla getGuerilla(){
        assert !guerillas.empty();
        this.numGuerillas--;
        return this.guerillas.dequeue();
    }

    /**
     * Remove a hostage from the head of the cave.
     * @return The hostage at the top of the cave (StackNode).
     * @pre The cave must not be empty.
     */
    private Hostage getHostage(){
        assert !hostages.empty();
        this.numHostages--;
        return this.hostages.pop();
    }

    /**
     * Retreive the number of guerillas in the guard line.
     * @return The number of guerillas in the guard line.
     */
    public int getNumGuerillas(){
        return this.numGuerillas;
    }

    /**
     * Retrieve the total number of hostages in the cave.
     * @return The total number of hostages in the cave.
     */
    public int getNumHostages(){
        return this.numHostages;
    }

    /**
     * A soldier enters the enemy base and tries to rescue a hostage.
     * @param soldier The soldier who attempts to rescue the hostage.
     * @return A hostage if the rescue attempt was a success, otherwise, if the rescue attempt failed, return null.
     */
    public Hostage rescueHostage(Soldier soldier){
        System.out.println(soldier.toString() + " enters enemy base...");
        Hostage hostage = getHostage();

        // Return hostage if there are no more guerillas left.
        if (this.getNumGuerillas() == 0){
            return hostage;
        }

        // Get the guerilla from the guard line.
        Guerilla guerilla = getGuerilla();

        // Create random number for dice roll. The guerilla rolls the dice (1-100).
        Random randomNumber = new Random();

        // nextInt(int bound) documentation:
        // Returns int value between 0 (inclusive) and the specified value (exclusive).
        int numberGuerilla = randomNumber.nextInt(101);

        // If the die roll is greater than the chance to defeat the soldier, the soldier declares victory over the
        // guerilla and the guerilla declares defeat to the soldier. The front hostage is then returned from the method.
        System.out.println(soldier.toString() + " battles " + guerilla.toString() + " who rolls a " + numberGuerilla);
        if (numberGuerilla > Guerilla.CHANCE_TO_BEAT_SOLDIER){
            soldier.victory(guerilla);
            guerilla.defeat(soldier);
            return hostage;
        }

        // Otherwise the guerilla declares victory over the soldier and the soldier declares defeat to the guerilla.
        // The hostage is pushed back onto the head of cave and the guerilla is added to the end of the guard line.
        // No hostage is returned (return null). Do NOT re-add soldier to bunker (QueueNode).
        else{
            guerilla.victory(soldier);
            soldier.defeat(guerilla);
            addHostage(hostage);
            addGuerilla(guerilla);
            return null;
        }
    }
}
