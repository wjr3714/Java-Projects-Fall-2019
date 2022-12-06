//
// Stubbed Player class
// TODO: use as a starting point for your player.
// NOTE: extra classes that help your player must be in the same package.
//

package Players.Wjr3714;

import Interface.Coordinate;
import Interface.PlayerModulePart1;
import Interface.PlayerMove;
import java.util.ArrayList;

/**
 * Represents a player and the functions that a player must implement for the Pathbuilder game.
 *
 * @author William J. Reid (wjr3714)
 */
public class Wjr3714 implements PlayerModulePart1 {

    private Pathbuilder_Graph board;
    private int playerID;

    @Override
    public boolean hasWonGame( int playerID ) {

        ArrayList<Coordinate> path = new ArrayList<>();

        for (int i = 1; i < this.board.getGraphDimensions(); i+=2){
            // Player #1
            if (playerID == 1){
                path.add(new Coordinate(i,0));
            }
            // Player #2
            else if (playerID == 2){
                path.add(new Coordinate(0,i));
            }
        }

        return this.board.searchBFS(path, playerID); // Determine if a path exists from start to finish for either player.
    }

    @Override
    public void initPlayer( int i, int playerID ) {
        // Create a board game representation that each player will use to devise his/her strategy.
        this.board = new Pathbuilder_Graph(i);
        this.playerID = playerID;

    }

    @Override
    public void lastMove( PlayerMove playerMove ) {
        Coordinate coordinate = playerMove.getCoordinate();
        int playerID = playerMove.getPlayerId();

        int row = coordinate.getRow();
        int col = coordinate.getCol();

        this.board.getPathbuilderGraph()[row][col] = playerID;

    }

    @Override
    public void otherPlayerInvalidated() {
    }

    @Override
    public PlayerMove move() {

        // TODO: use internal documentation to describe your algorithm.

        // TODO: you will need to add private methods as helpers.
        // TODO: you may also need to add other .java files to your package.

        throw new RuntimeException();
//        return null;
    }
}
