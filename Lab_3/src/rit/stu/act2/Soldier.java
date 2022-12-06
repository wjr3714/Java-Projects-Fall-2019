package rit.stu.act2;

/**
 * A class to represent a soldier. Each soldier has a unique ID (represented by an integer).
 * @param <T> The type of player, in this case 'Soldier'.
 * @see Player
 * @author William J. Reid (wjr3714)
 */
public class Soldier<T> implements Player<T> {

    /** The soldier's ID */
    private int id;

    /** Create a new soldier. */
    public Soldier(int id){
        this.id = id;
    }

    /** If the soldier is defeated by another player, display the message, "{soldier} cries, 'Besiegt von {player}!'". */
    public void defeat(T player){
        System.out.println(this.toString() + " cries, 'Besiegt von " + player.toString() + "!'");
    }

    /** If the soldier is defeats another player, display the message, "{soldier} yells, 'Sieg über {player}!'". */
    public void victory(T player){
        System.out.println(this.toString() + " yells, 'Sieg über " + player.toString()+"!'");
    }

    /**
     * Return the string representation of a soldier.
     * @return The soldier's ID.
     * */
    public String toString(){
        return "Soldier #" + this.id;
    }

}
