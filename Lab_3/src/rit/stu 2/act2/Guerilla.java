package rit.stu.act2;

/**
 * A class to represent a guerilla. Each Guerilla has a unique ID.
 * @param <T> The type of player, in this case 'Guerilla'.
 * @see Player
 * @author William J. Reid (wjr3714)
 */
public class Guerilla<T> implements Player<T> {

    /** The odds of the guerilla to defeat a soldier. */
    public static final int CHANCE_TO_BEAT_SOLDIER = 20;

    /** The ID of the current guerilla player. */
    private int id;

    /** Create a new guerilla player. */
    public Guerilla(int id){
        this.id = id;
    }

    /**
     * If the guerilla defeats a player, display the message, "{guerilla} yells, 'Victoria sobre {player}!'".
     * @param player The defeated player.
     * */
    public void victory(T player){
        System.out.println(this.toString() + " yells, 'Victoria sobre " + player.toString() + "!'");
    }

    /**
     * If the guerilla is defeated by a player, display the message, "{guerilla} cries, 'Derrotado por {player}!'".
     * @param player The player that defeated this guerilla.
     * */
    public void defeat(T player){
        System.out.println(this.toString() + " cries, 'Derrotado por " + player.toString() + "!'");
    }

    /**
     * The string representation of a guerilla.
     * @return "Guerilla #{ID}", replacing "{ID}" by the guerilla's id.
     */
    public String toString(){
        return "Guerilla #" + this.id;
    }
}
