package heroes;

import game.Team;

/**
 * The Berserker is a type of hero with the following characteristics: low amount of hit points but largest amount of
 * damage to its opponents.
 *
 * @see Hero
 * @see Team
 *
 * @author William J. Reid (wjr3714)
 */
public class Berserker extends Hero {

    /** The berserker's initial and maximum hit points. */
    protected static final int BERSERKER_HIT_POINTS = 30;
    /** The amount of damage inflicted by berserker upon attack. */
    protected static final int DAMAGE_AMOUNT = 20;

    /**
     * Create Berserker for either the DRAGON or LION team.
     * @param team The Berserker's team.
     */
    protected Berserker(Team team) {
        super(Heroes.getName(team, Heroes.Role.BERSERKER), BERSERKER_HIT_POINTS);
    }

    @Override
    public Heroes.Role getRole(){
        return Heroes.Role.BERSERKER;
    }

    /**
     * Get the damage that the berserker inflicts onto its opponents.
     * @return The damage that the berserker inflicts onto its opponents.
     */
    @Override
    public int getDamageAmount(){
        return DAMAGE_AMOUNT;
    }

}
