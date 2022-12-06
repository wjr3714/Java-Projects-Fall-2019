package heroes;

import heroes.Heroes.Role;
import game.Team;

/**
 * The Healer is a type of hero with the following characteristics: moderate amount of hit points, does the least
 * amount of damage, heals all non-defeated party members and itself while attacking.
 *
 * @see Hero
 * @see Team
 *
 * @author William J. Reid (wjr3714)
 */
public class Healer extends Hero {

    /** The amount the healer heals each of its teammates when attacking. */
    protected static final int HEAL_AMOUNT = 10;
    /** The amount of damage inflicted by the healer when attacking the opponent. */
    protected static final int DAMAGE_AMOUNT = 10;
    /** The healer's initial (and maximum) hit points. */
    protected static final int HEALER_HIT_POINTS = 35;

    /**
     * Create a Healer for the two teams, DRAGON and LION.
     * @param team The healer's team.
     * @param party The healer's party.
     */
    protected Healer(Team team, Party party) {
        super(Heroes.getName(team, Role.HEALER), HEALER_HIT_POINTS);
    }

    /**
     * When a healer attacks, they first heal themselves. Then, they heal the rest of their party. Aftwerwards,
     * they attack the enemy and inflict damage.
     * @param enemy The opponent attacked by the healer.
     */
    @Override
    public void attack(Hero enemy){

        // First, the healer heals itself.
        this.heal(HEAL_AMOUNT);

        // Then, the healer heals it's teammates (if any teammates remain alive).
        for (Hero eachHero: this.getParty().getHeroes()){
            eachHero.heal(HEAL_AMOUNT);
        }

        // Lastly, the healer inflicts damage onto the opponent.
        enemy.takeDamage(DAMAGE_AMOUNT);
    }

    /**
     * Get the role of the Healer.
     * @return The role of the hero Healer.
     */
    @Override
    public Heroes.Role getRole(){
        return Heroes.Role.HEALER;
    }

    /**
     * Get the damage that the healer inflicts onto its opponents.
     * @return The damage that the healer inflicts onto its opponents.
     */
    @Override
    public int getDamageAmount(){
        return DAMAGE_AMOUNT;
    }

}
