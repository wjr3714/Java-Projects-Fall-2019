package game;

import heroes.Hero;
import heroes.Heroes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A party represents the  collection of non-fallen teammates. A party behaves like a queue (FIFO).
 * The hero at the front of the queue will fight next. If the hero is not defeated after a battle, the hero is added to
 * the back of the queue and will live to fight another day.
 *
 * @see heroes.Party
 * @see Team
 * @see heroes.Berserker
 * @see heroes.Healer
 * @see heroes.Tank
 *
 * @author William J. Reid (wjr3714)
 *
 */
public class HeroParty implements heroes.Party{
    /** The team associated to the party. */
    private Team team;
    /** The team & party's heroes. */
    private ArrayList<Hero> heroes;


// Per JavaDocs for Lab 4...

//   [Seed Value]	          [Hero Order]
//         0	        Tank, Healer, Berserker
//         1	        Healer, Tank, Berserker
//         2		    Tank, Berserker, Healer
//         3		    Berserker, Healer, Tank
//         5		    Healer, Berserker, Tank
//         7		    Berserker, Tank, Healer
    /**
     * Create the party for the Berserker, Healer and Tank.
     * @param team The team associated to the hero's party.
     * @param seed The random number generator.
     */
    public HeroParty(Team team, int seed){
        this.team = team;
        this.heroes = new ArrayList<>();
        Collections.shuffle(this.heroes, new Random(seed));

        if (seed == 0) {
            this.addHero(Hero.create(Heroes.Role.TANK,team,this));
            this.addHero(Hero.create(Heroes.Role.HEALER,team,this));
            this.addHero(Hero.create(Heroes.Role.BERSERKER,team,this));
        }
        else if(seed == 1){
            this.addHero(Hero.create(Heroes.Role.HEALER,team,this));
            this.addHero(Hero.create(Heroes.Role.TANK,team,this));
            this.addHero(Hero.create(Heroes.Role.BERSERKER,team,this));
        }
        else if(seed == 2){
            this.addHero(Hero.create(Heroes.Role.TANK,team,this));
            this.addHero(Hero.create(Heroes.Role.BERSERKER,team,this));
            this.addHero(Hero.create(Heroes.Role.HEALER,team,this));
        }
        else if(seed == 3){
            this.addHero(Hero.create(Heroes.Role.BERSERKER,team,this));
            this.addHero(Hero.create(Heroes.Role.HEALER,team,this));
            this.addHero(Hero.create(Heroes.Role.TANK,team,this));
        }
        else if(seed == 5){
            this.addHero(Hero.create(Heroes.Role.HEALER,team,this));
            this.addHero(Hero.create(Heroes.Role.BERSERKER,team,this));
            this.addHero(Hero.create(Heroes.Role.TANK,team,this));
        }
        else if(seed == 7){
            this.addHero(Hero.create(Heroes.Role.BERSERKER,team,this));
            this.addHero(Hero.create(Heroes.Role.TANK,team,this));
            this.addHero(Hero.create(Heroes.Role.HEALER,team,this));
        }
        else {
            this.addHero(Hero.create(Heroes.Role.TANK,team,this));
            this.addHero(Hero.create(Heroes.Role.HEALER,team,this));
            this.addHero(Hero.create(Heroes.Role.BERSERKER,team,this));
        }
    }

    /**
     * Add a hero to the back of the collection.
     * @param hero The new hero added to the collection.
     */
    @Override
    public void addHero(Hero hero) {
        this.getHeroes().add(hero);
    }

    /**
     * Get all heroes in the party that are still alive.
     * @return List of the hero's still alive (i.e., the party).
     */
    @Override
    public List<Hero> getHeroes() {
        return this.heroes;
    }

    /**
     * Get the party's team.
     * @return The party's team.
     */
    @Override
    public Team getTeam() {
        return this.team;
    }

    /**
     * Return the number of heroes still alive.
     * @return The number of non-fallen heroes.
     */
    @Override
    public int numHeroes() {
        return this.heroes.size();
    }

    /**
     * Remove hero at the front of the collection.
     * @return The hero at the front of the collection.
     */
    @Override
    public Hero removeHero() {

        // Get hero at he front of the Queue.
        Hero hero = this.heroes.get(0);

        // Remove hero at front of the Queue.
        this.heroes.remove(0);

        return hero;
    }

    /**
     * Return string representation of the party.
     * @return A string representation of the hero's party.
     */
    public String toString(){
        if (this.team == Team.LION){
            return "LION";
        }
        else {
            return "DRAGON";
        }
    }
}
