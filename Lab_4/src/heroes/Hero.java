package heroes;

import game.Team;

/**
 * This class is a blue print for all heroes in the game of storms.
 *
 * @see Berserker
 * @see Healer
 * @see Tank
 * @see Party
 *
 * @author William J. Reid (wjr3714)
 */
public abstract class Hero {

    /** The name of the hero. */
    private String name;
    /** The party the hero belongs to. */
    private Party party;
    /** The team the hero belongs to. */
    private Team team;
    /** The current hit points of the hero. */
    private int currentHealth;
    /** The maximum hit points of the hero. */
    private int hitPoints;
    /** The maximum damage a hero can inflict on its opponent. */
    private int maximumDamage;


    protected Hero(String name, int hitPoints){
        this.name = name;
        this.currentHealth = hitPoints;
        this.hitPoints = hitPoints;
    }

    /**
     * The hero attacks an opponent.
     * @param enemy The opponent being attacked.
     */
    public void attack(Hero enemy){
        enemy.takeDamage(this.maximumDamage);
    }

    /**
     * Create a hero of a particular role for a certain team.
     * @param role The role of the hero.
     * @param team The team the hero will belong to.
     * @param party The hero's party (only used by healer for healing)
     * @return The hero.
     */
    public static Hero create(Heroes.Role role, Team team, Party party){
        Hero aHero;
        if (role == Heroes.Role.BERSERKER){
            aHero = new Berserker(team);
            aHero.maximumDamage = aHero.getDamageAmount();
        }
        else if (role == Heroes.Role.HEALER){
            aHero = new Healer(team, party);
            aHero.maximumDamage = aHero.getDamageAmount();
        }
        else{
            aHero = new Tank(team);
            aHero.maximumDamage = aHero.getDamageAmount();
        }
        aHero.party = party;
        aHero.team = team;
        return aHero;
    }


    /**
     * Get the name of the hero.
     * @return The hero's name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get Hero's role
     * @return Hero's role.
     */
    public abstract Heroes.Role getRole();

    /**
     * If the current hit points is ≤ 0, then the hero has fallen.
     * @return True if current hit points > 0, false otherwise.
     */
    public boolean hasFallen(){
        if (this.currentHealth <= 0){
            System.out.println(this.getName() + " has fallen!");
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * The healer heals all teammates.
     * @param amount The amount the Hero heals.
     */
    public void heal(int amount){
        this.currentHealth = this.currentHealth + amount;
        if (this.currentHealth > this.hitPoints){
            this.currentHealth = this.hitPoints;
        }
        System.out.println(this.getName() + " heals " + amount + " points");
    }

    /**
     * The hero takes some damage.
     * @param amount The amount of the damage received.
     */
    public void takeDamage(int amount){
        // Hero takes damage.
        this.currentHealth = this.currentHealth - amount;
        System.out.println(this.getName() + " takes " + amount + " damage");

        // The current health cannot be negative.
        if (this.currentHealth < 0){
            this.currentHealth = 0;
        }
    }


    /**
     * Returns the string representation of the Hero in the form: {name}, {ROLE}, #/#
     * @return The string representation of the Hero
     */
    @Override
    public String toString(){
        return this.getName() + ", " + this.getRole() + ", " + this.getCurrentHealth() + "/" + this.getMaximumHealth();
    }

    /**
     * Returns the hero's current health.
     * @return The hero's current health.
     */
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    /**
     * Returns the party of the Hero
     * @return the party of the Hero
     */
    public Party getParty() {
        return party;
    }

    /**
     * Returns maximum number of hit points of the hero.
     * @return The maximum number of hit points of the hero.
     */
    public int getMaximumHealth(){
        return this.hitPoints;
    }

    /**
     * Returns the hero's team.
     * @return The hero's team.
     */
    public Team getTeam(){
        return this.team;
    }

    /**
     * Get the damage that this inflicts onto its opponents.
     * @return The maximum damage this hero can inflict onto opponent.
     */
    public abstract int getDamageAmount(); // Get damage amount from subclasses (Berserker, Healer, Tank).

}
