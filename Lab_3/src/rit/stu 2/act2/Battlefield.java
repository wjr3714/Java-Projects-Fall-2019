/*
 * Battlefield.java
 */

package rit.stu.act2;

import java.util.Random;

/**
 * The main simulation class is run on the command line as:<br>
 * <br>
 * $ java Battlefield #_hostages #_soldiers #_guerillas
 *
 * This class simulates the rescue attempt of soldiers who exit a bunker in search of hostages imprisoned in a cave by guerillas.
 * If the soldier defeats the guerilla, the soldier can retrieve one hostage from the cave. Before taking the hostage to safety (a chopper)
 * the soldier must defeat a predator (never dies, but can be defeated). If the soldier fails to defeat the predator, the soldier dies.
 * The hostage then tries to defeat the predator. If the hostage wins, it boards the chopper. Otherwise, the hostage dies. However,
 * if the soldier defeats the predator, he takes the hostage to the chopper, then returns to the bunker (last of position in line),
 * and waits for his/her turn again to rescue more hostages from the cave. If no more hostages are alive but the bunker still has
 * soldiers, the soldiers can board the chopper without fighting the Predator or guerilla (because the chopper is very close to the bunker).
 * If all guerillas are dead but soldiers and and hostages remain, the soldiers and hostages only need to fight the predator
 * before boarding the chopper.
 *
 * NOTE: If all soldiers die, and all guerillas die, but there are still hostages left, although the hostages could leave the cave,
 * they could never reach the chopper as they don't know where it is (they need to be escorted to the chopper by a soldier).
 *
 * @see Bunker
 * @see Chopper
 * @see EnemyBase
 * @see Player
 * @see Soldier
 * @see Hostage
 * @see Guerilla
 * @see Predator
 *
 * @author Sean Strout @ RIT CS
 * @author William J. Reid (wjr3714)
 */
public class Battlefield {

    /** The single instance of the random number generator. */
    private final static Random rng = new Random();

    /** The seed the random number generator will use. */
    private final static int SEED = 0;

    /** The bunker of soldiers. */
    private Bunker bunker;

    /** The chopper. */
    private Chopper chopper;

    /** The enemy base containing hostages and guerillas. */
    private EnemyBase enemyBase;

    /** The predator! */
    private Predator predator;


    /**
     * Generate a random integer between min and max inclusive.  For example: <br>
     * <br>
     * <tt>Battlefield.nextInt(1, 5): A random number, 1-5</tt><br>
     * <br>
     *
     * @param min The smallest value allowed.
     * @param max The largest value allowed.
     * @return A random integer.
     */
    public static int nextInt(int min, int max) {
        return rng.nextInt(max - min + 1) + min;
    }

    /**
     * Create the battlefield.  This method is responsible for seeding the
     * random number generator and initializing all the supporting classes
     * in the simulation: Chopper, EnemyBase, Bunker and Predator.
     *
     * @param numHostages number of hostages being held in enemy base at start
     * @param numSoldiers number of soldiers to rescue the hostages at start
     * @param numGuerillas number of guerillas in the enemy base at start
     */
    public Battlefield(int numHostages, int numSoldiers, int numGuerillas) {
        Battlefield.rng.setSeed(SEED);
        this.bunker = new Bunker(numSoldiers);
        this.enemyBase = new EnemyBase(numHostages, numGuerillas);
        this.chopper = new Chopper();
    }

    /**
     * Prints out the current statistics of the simulation in terms of the number
     * of hostages, soldiers, guerillas, and rescued people are.  The format
     * of the message is on a single line:<br>
     * <br>
     * Statistics: # hostages remain, # soldiers remain, # guerillas remain, # rescued<br>
     */
    private void printStatistics() {
        System.out.println(this.enemyBase.getNumHostages() + " hostages remain, " + this.enemyBase.getNumGuerillas() + " guerillas remain, "
                + this.bunker.getNumSoldiers() + " soldiers remain, " + this.chopper.getNumRescued() + " rescued");
    }

    /**
     * The main battle simulation loop runs here.  The simulation runs until either
     * all the hostages have been rescued, or there are no more soldiers left
     * to rescue hostages.  The steps to follow are:<br>
     * <br>
     * 1. Print the start up message, "Get to the choppa!"<br>
     * <br>
     * 2. While the simulation is still going:<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 3. Print the current statistics<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 4. Deploy the next soldier into the enemy base to
     * attempt rescuing the next hostage.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 5. If the hostage was not rescued goto step 2.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 6. Otherwise do steps 7-X.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 7. Print a message "{hostage} rescued from enemy
     * base by soldier {solder}".<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 8. Roll the die, 1-100, to get the predators chance to
     * defeat the soldier and display the message, "{soldier} encounters the
     * predator who rolls a #".<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 9. If the die roll is greater than the predator's
     * chance to defeat the soldier, declare victory of the soldier over the predator
     * and defeat of the predator to the soldier.  Add the soldier back to the
     * bunker and go to step 2.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 10. Otherwise declare victory of the predator over the
     * soldier and defeat of the soldier to the predator and continue to step 11.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 11. Roll the die again, 1-100, to get the predators
     * chance to defeat a hostage and display the message, "{hostage} encounter the
     * predator who rolls a #".<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 12. If the die roll is less than or equal to the predator's
     * chance to default the hostage, declare victory of the predator over the hostage
     * and defeat of the hostage to the predator.  Go to step 2.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 13. Otherwise declare victory of the hostage over the
     * predator and defeat of the predator to the hostage and then have the
     * hostage board the chopper.  Go to step 2.<br>
     * <br>
     * 14. At the end of the simulation any soldier that may be left in the bunker
     * should board the chopper.<br>
     * <br>
     * 15. If there are any remaining passengers on the chopper, rescue them to safety.<br>
     * <br>
     * 16. Print the statistics one last time.<br>
     */
    private void battle() {

        // Print the start up message.
        System.out.println("Get to the choppa!");

        // While the simulation is still going, print the current statistics
        printStatistics();

        // If the enemy base has hostages and the bunker has soldiers, soldier should try to rescue a hostage.
        while (this.enemyBase.getNumHostages()!=0 && this.bunker.hasSoldiers()){

            // Check there are soldiers in bunker and deploy them.
            Soldier soldier = bunker.deployNextSoldier();

            // Battle the guerilla.
            Hostage hostage = enemyBase.rescueHostage(soldier);


            /* If the soldier lost against the guerilla, return to top of while loop and try to deploy additional
               soldier (if any are left). */
            if (hostage != null){

                // Case: Soldier defeats guerilla.
                System.out.println(hostage + " rescued from enemy base by "+ soldier);
                predator = new Predator();
                Random randomNumber = new Random();
                int PredatorDiceRollSoldier = randomNumber.nextInt(101);
                System.out.println(soldier + " encounters the predator who rolls a " + PredatorDiceRollSoldier);

                // Case: Solider defeats predator.
                if (PredatorDiceRollSoldier > Predator.CHANCE_TO_BEAT_SOLDIER){
                    soldier.victory(predator);
                    predator.defeat(soldier);
                    this.chopper.boardPassenger(hostage);
                    this.bunker.fortifySoldiers(soldier); // Return soldier to end of queue in bunker.
                    printStatistics();
                }
                // Case: Predator defeats soldier.
                else{
                    predator.victory(soldier);
                    soldier.defeat(predator);

                    // Roll dice again for Predator to battle hostage.
                    Random numberRandom = new Random();
                    int PredatorDiceRollHostage = numberRandom.nextInt(101);
                    System.out.println(hostage + " encounters the predator who rolls a " + PredatorDiceRollHostage);

                    // Case: Predator defeats hostage.
                    if (PredatorDiceRollHostage <= Predator.CHANCE_TO_BEAT_HOSTAGE) {
                        predator.victory(hostage);
                        hostage.defeat(predator);
                        printStatistics();
                    }

                    // Case: Hostage defeats predator.
                    else {
                        hostage.victory(predator);
                        predator.defeat(hostage);
                        this.chopper.boardPassenger(hostage);
                        System.out.println("Chopper transported " + hostage + " to safety!");
                        printStatistics();
                    }
                }
            }
        }

        /* If all hostages are rescued (or none are left in the simulation), but there are remaining soldiers,
           take the soldiers to safety! Remove soldier from queue (bunker) and add to chopper.
           NOTE: If there are still some guerillas left but no hostages, soldiers can safely make it to the chopper
           without a fight. */
        while (bunker.hasSoldiers()){
            Soldier survivingSoldier = bunker.deployNextSoldier();
            this.chopper.boardPassenger(survivingSoldier);
        }

        /* If all hostages & soldiers left in the simulation are in the chopper but the chopper is not full,
           send them to safety. */
        if (this.chopper.getNumPassengers() > 0){
            this.chopper.rescuePassengers();
        }

        // Print the final statistics of the simulation.
        printStatistics();

    } // End the simulation.

    /**
     * The main method expects there to be three command line arguments:<br>
     * <br>
     *  1: the number of hostages (a positive integer)<br>
     *  2: the number of soldiers (a positive integer)<br>
     *  3: the number of guerillas (a positive integer)<br>
     * <br>
     * If all the arguments are supplied it will create the battle field
     * and then begin the battle.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Battlefield #_hostages #_soldiers #_guerillas");
        } else {
            Battlefield field = new Battlefield(
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]));
            field.battle();
        }
    }

}
