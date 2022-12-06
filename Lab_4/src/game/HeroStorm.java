package game;

import heroes.Hero;

/**
 * This is the main class for the RPG game, Super Fantasy Hero Storm. This class plays the game.
 * The program is run on the command line as:
 *
 * $ java HeroStorm dragon_seed_# lion_seed_#
 *
 * The seeds are two integers for generating random numbers when shuffling the 3 heroes of both teams (DRAGON & LION).
 *
 * @see Team
 * @see HeroParty
 * @see heroes.Party
 * @see Hero
 *
 * @author William J. Reid (wjr3714)
 */
public class HeroStorm {
    /** The party of lion heroes.*/
    private HeroParty lions;
    /** The party of dragon heroes. */
    private HeroParty dragons;
    /** The battle number.*/
    private int battleNumber;

    /**
     * Create each of the two parties, initializes the battle counter.
     * @param dragonSeed The seed for the DRAGON random number generator.
     * @param lionSeed The seed for the LION random number generator
     */
    public HeroStorm(int dragonSeed, int lionSeed){
        this.dragons = new HeroParty(Team.DRAGON, dragonSeed);
        this.lions = new HeroParty(Team.LION, lionSeed);
        this.battleNumber = 1;
    }

    /**
     * Plays each battle. A battle consists of an attack by the two heroes located at the front of each team's queues.
     * These two opponents are temporarily removed from the party. The first hero (of a team) to attack alternates every
     * round, (team DRAGON attacks during the first round). If the hero is not defeated after the attack, they can
     * attack the hero who attacked first back. Afterward both hero's have attacked once, each non-defeated hero
     * is added to the back of their party. Defeated heroes die ("disappear") with a farewell message. The battles
     * continue until all hero's from one team have passed away. The team with heroes still alive is declared victorious.
     * No user input is required, the game behaves as a simulation.
     */
    public void play(){ // Exit method only after the game is over.

        // Play until either team (LION & DRAGON) has no remaining heroes.
        while (this.lions.getHeroes().size() > 0 && this.dragons.getHeroes().size() > 0) {
            // Display battle number
            System.out.println("Battle #" + this.battleNumber);
            System.out.println("==========");

            // Display team Dragon
            System.out.println(this.dragons.toString() + ":");
            for (int i = 0; i < this.dragons.getHeroes().size(); i++) {
                System.out.println(this.dragons.getHeroes().get(i).toString());
            }

            // Display empty line per sample output provided.
            System.out.println();

            // Display team Lion
            System.out.println(this.lions.toString() + ":");
            for (int i = 0; i < this.lions.getHeroes().size(); i++) {
                System.out.println(this.lions.getHeroes().get(i).toString());
            }

            // Dragon team attacks first for odd battle numbers of simulation.
            if (this.battleNumber % 2 != 0) {
                // Display heroes from each team at the front of the queue in the order that they will attack.
                System.out.println("\n*** " + this.dragons.getHeroes().get(0).getName() + " vs " + this.lions.getHeroes().get(0).getName() + "! \n");

                // Get the heroes at the front of the queue who will battle each other.
                Hero dragonHero = this.dragons.getHeroes().get(0);
                Hero lionHero = this.lions.getHeroes().get(0);
                this.dragons.removeHero();
                this.lions.removeHero();

                // Team DRAGON's hero attacks first.
                dragonHero.attack(lionHero);

                // If lion hero is still alive...
                if (!lionHero.hasFallen()){
                    // Lion attacks
                    lionHero.attack(dragonHero);
                    // Add lion hero to end if queue as current hit points of lion hero > 0.
                    this.lions.addHero(lionHero);
                }

                // Add dragon hero to end if queue if the current hit points of dragon hero > 0.
                if (!dragonHero.hasFallen()){
                    this.dragons.addHero(dragonHero);
                }

            }

            // Lion team attacks first for even battle numbers.
            else{

                // Display heroes from each team at the front of the queue in the order that they will attack.
                System.out.println("\n*** " + this.lions.getHeroes().get(0).getName() + " vs " + this.dragons.getHeroes().get(0).getName() + "!\n");

                // Get the heroes at the front of the queue who will battle each other.
                Hero dragonHero = this.dragons.getHeroes().get(0);
                Hero lionHero = this.lions.getHeroes().get(0);
                this.dragons.removeHero();
                this.lions.removeHero();

                // Team LION's hero attacks first.
                lionHero.attack(dragonHero);

                // If the dragon hero is still alive...
                if (!dragonHero.hasFallen()){

                    // Dragon hero attacks!
                    dragonHero.attack(lionHero);

                    // Add dragon hero to end if queue as current hit points of dragon hero > 0.
                    this.dragons.addHero(dragonHero);
                }
                // Add lion hero to end if queue if the current hit points of lion hero > 0.
                if (!lionHero.hasFallen()){
                    lions.addHero(lionHero);
                }

            }

            // Increment battle number.
            this.battleNumber++;

            // Print an empty line.
            System.out.println();
        }

        // Display which team won and which team lost.
        if (this.lions.getHeroes().size() == 0){
            System.out.println("Team Dragon wins!");
        }
        else {
            System.out.println("Team Lion wins!");
        }
    }

    /**
     * Runs the simulation of the RPG game, Super Fantasy Hero Storm.
     * @param args Command line arguments. These are the seeds (two integers) used for the random number generators when
     *             two teams of 3 heroes is shuffled.
     */
    public static void main(String[] args){
        if (args.length == 2) {
            // Create the game & "play" it (this is really a simulation more than a game...)
            HeroStorm game = new HeroStorm(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            game.play();
        }
        else{
            System.out.println("Usage: java HeroStorm dragon_seed_# lion_seed_#");
        }
    }
}
