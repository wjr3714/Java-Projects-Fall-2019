package heroes;

import game.Team;

/**
 * The tank is a type of hero with the following characteristics: has the most hit points and inflicts moderate damage
 * onto its opponents. Additionally, a tank is able to mitigate 10% of the damage received by opponents through the
 * use of its shield.
 *
 * @see Hero
 * @see Team
 *
 * @author William J. Reid (wjr3714)
 */
public class Tank extends Hero {

    /** Amount of damage a tank inflicts on the opponent. */
    protected static final int DAMAGE_AMOUNT = 15;
    /** The tank's initial and maximum hit points. */
    protected static final int TANK_HIT_POINTS = 40;
    /** The incoming damage amount is multiplied by this fraction to reduce the actual damage received by the tank. */
    protected static final double SHIELD_DMG_MULTIPLIER = 0.9;

    /**
     * Create Tank hero for a team.
     * @param team The tank's team.
     */
    protected Tank(Team team) {
        super(Heroes.getName(team, Heroes.Role.TANK), TANK_HIT_POINTS);
    }

    /**
     * The tank is the only hero capable of mitigating the damage received. This special ability is thanks to their shield.
     * The damage they received is reduced by a factor, and then they take the damage in the same way as the other heroes,
     * that is, by calling the takeDamage method in the parent class hero.
     * Note: The damage to be received is multiplied by the SHIELD_DMG_MULTIPLIER, and then truncated to an integer.
     * @param amount Initial amount of damage the tank will take, before applying mitigation by shield.
     */
    @Override
    public void takeDamage(int amount){
        super.takeDamage((int) (amount * SHIELD_DMG_MULTIPLIER));
    }

    /**
     * Get the Tank's role.
     * @return The Tank's role.
     */
    @Override
    public Heroes.Role getRole(){
        return Heroes.Role.TANK;
    }

    /**
     * Get the damage that the tank inflicts onto its opponents.
     * @return The damage that the tank inflicts onto its opponents.
     */
    @Override
    public int getDamageAmount(){
        return DAMAGE_AMOUNT;
    }

}
