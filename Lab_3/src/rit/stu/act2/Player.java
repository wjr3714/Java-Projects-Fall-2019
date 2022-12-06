package rit.stu.act2;

/**
 * This interface represents a player in the game. A player will come into conflict with another player,
 * and the result of the battle will be one victor and one loser.
 * @param <T> The type of player can be a Guerilla, Hostage, Predator, or Soldier
 */
public interface Player<T> {

    /**
     * Do this when this player loses to another player.
     * @param player The player that defeated this player.
     */
    public void defeat(T player);

    /**
     * Do this when this player defeats another player.
     * @param player The player defeated.
     */
    public void victory(T player);
}
