package rit.stu.act2;

/**
 * A class to represent the predator. Used to display the messages of victory and defeat.
 * @param <T> The type of player, in this case 'Predator'.
 * @see Player
 * @author William J. Reid (wjr3714)
 */
public class Predator<T> implements Player<T> {

    /**
     * The chance the predator defeats a hostage. If roll of die (1-100) is ≤ to this,
     * the predator defeats the hostage, otherwise the predator loses.
     */
    public static final int	CHANCE_TO_BEAT_HOSTAGE = 75;

    /**
     * The chance the predator defeats a soldier. If roll of die (1-100) is ≤ to this,
     * the predator defeats the soldier, otherwise the predator loses.
     */
    public static final int	CHANCE_TO_BEAT_SOLDIER = 50;

    // Empty constructor based on the provided JavaDocs.
    public Predator(){}

    /**
     * If the predator wins, they display the message, "The predator yells out in triumphant victory over {player}!".
     * @param player The player defeated.
     */
    @Override
    public void victory(T player){
        System.out.println("The predator yells out in triumphant victory over " + player.toString() + "!");
    }

    /**
     * If the predator loses, the display the message, "The predator cries out in glorious defeat to {player}!".
     * @param player The player that defeated the Predator.
     */
    @Override
    public void defeat(T player){
        System.out.println("The predator cries out in glorious defeat to " + player.toString() + "!");
    }

    /**
     * The string representation of the predator player.
     * @return The string name of the player, "Predator"
     */
    public String toString(){
        return "Predator";
    }
}
