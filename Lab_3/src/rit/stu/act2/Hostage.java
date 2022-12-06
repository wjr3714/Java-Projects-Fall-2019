package rit.stu.act2;

/**
 * A class to represent a hostage. Each hostage has a unique ID.
 * @param <T> The type of player, in this case 'Hostage'.
 * @see Player
 * @author William J. Reid (wjr3714)
 */
public class Hostage<T> implements Player<T> {

    /** The ID of the current hostage player. */
    private int id;

    /** Create a new hostage. */
    public Hostage(int id){
        this.id = id;
    }

    /**
     * If the hostage defeats player, display the message, "{hostage} yells, 'Victory over {player}!'".
     * @param player The player defeated.
     */
    public void victory(T player){
        System.out.println(this.toString() + " yells, 'Victory over " + player.toString() + " !'");
    }

    /**
     * If the hostage is defeated by another player, display the message, "{hostage} cries, 'Defeated by {player}!'".
     * @param player The player that defeated this player.
     */
    public void defeat(T player){
        System.out.println(this.toString() + " cries, 'Defeated by " + player.toString() + "!'");
    }

    /**
     * The string representation of a hostage.
     * @return "Hostage #{ID}", replacing "{ID}" by the hostage's id.
     */
    public String toString(){
        return "Hostage #" + this.id;
    }
}
