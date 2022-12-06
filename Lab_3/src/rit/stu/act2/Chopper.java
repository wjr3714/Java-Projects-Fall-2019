package rit.stu.act2;

import rit.stu.act1.StackNode;

/**
 * A class that represents the chopper. The chopper can hold up to 6 passengers (soldiers and hostages).
 * There is only one door to the chopper that is accessible by the passengers. When they enter the chopper, they occupy
 * the seat closest to the door and any existing passengers move one seat down (StackNode). The chopper only flies
 * passengers to safety if the chopper is full or it is the last group of people to rescue.
 *
 * @see StackNode
 * @see Player
 *
 * @author William J. Reid (wjr3714)
 */
public class Chopper {

    // Stack = Last In, First Out (LIFO)
    // Use the methods implemented in the StackNode class to implement the methods below.

    /** The passengers (soldiers & hostages) are stored in a stack (StackNode). */
    private StackNode<Player> chopper;

    /** The maximum number of available seats in the chopper for soldiers and hostages. */
    public static final int MAX_OCCUPANCY = 6;

    /** The total number of passengers currently occupying seats in the chopper. */
    private int numPassengers;

    /** The total number of passengers that have been flown to safety (rescued). */
    private int numRescued;

    /**
     * Create the chopper. All the passenger seats are empty, no one has been rescued yet.
     */
    public Chopper(){
        this.chopper = new StackNode<Player>(); // Both soldiers and hostages can be placed in the stack (chopper).
        this.numPassengers = 0; // At the beginning, chopper is empty.
        this.numRescued = 0; // At the beginning, no passengers have been rescued.
    }

    /**
     * Board a passenger onto the chopper.
     * @param player The player (soldier or hostage) boarding the chopper.
     */
    public void boardPassenger(Player player){
        // Empty chopper if full, then add new player to chopper (stack).
        if (isFull()){
            rescuePassengers();
        }
        this.chopper.push(player);

        this.numPassengers++;
        System.out.println(player.toString() + " boards the chopper!");

    }

    /**
     * Get the total number of passengers rescued.
     * @return The total number of passengers rescued.
     */
    public int getNumRescued(){
        return this.numRescued;
    }

    /**
     * Check if the chopper is empty.
     * @return True if the chopper is empty, false otherwise.
     */
    public boolean isEmpty(){
        return this.chopper.empty();
    }

    /**
     * Check if the chopper is full.
     * @return True if the chopper is full, false otherwise.
     */
    public boolean isFull(){
        return this.chopper.size == MAX_OCCUPANCY;
    }

    /**
     * When the chopper is full, fly away and rescue the passengers. Also, if no more people are to be rescued
     * (i.e., the last group of soldiers and hostages), fly away and rescue these soldiers and hostages.
     */
    public void rescuePassengers(){

        while (!isEmpty()){

            // Print the players transported to safety.
            Player x = this.chopper.pop();
            System.out.println("Chopper transported " + x.toString() + " to safety!");

            // Update # players currently in chopper & # total players rescued.
            this.numPassengers--;
            this.numRescued++;
        }

    }

    /**
     * Get the total number of passengers currently occupying seats in the chopper.
     * @return Current number of passengers occupying seats in the chopper.
     */
    public int getNumPassengers(){
        return this.numPassengers;
    }

}
